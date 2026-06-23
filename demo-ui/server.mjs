// Zero-dependency static server + ingress proxy for the Restate 101 demo UI.
//
//   node server.mjs
//
// Serves the UI on http://localhost:8088 and forwards every /api/* request to the
// Restate ingress (http://localhost:8080/*). The proxy exists so the browser talks
// to the same origin it was served from — no CORS configuration needed.
import { createServer, request as httpRequest } from "node:http";
import { readFile } from "node:fs/promises";
import { fileURLToPath } from "node:url";
import { dirname, join, extname } from "node:path";

const PORT = Number(process.env.PORT ?? 8088);
const INGRESS = process.env.RESTATE_INGRESS ?? "http://localhost:8080";
const PUBLIC = join(dirname(fileURLToPath(import.meta.url)), "public");

const MIME = { ".html": "text/html", ".css": "text/css", ".js": "text/javascript", ".svg": "image/svg+xml" };

const server = createServer(async (req, res) => {
  // --- proxy: /api/<path>  ->  <INGRESS>/<path> ---
  if (req.url.startsWith("/api/")) {
    const target = new URL(INGRESS);
    const path = req.url.slice("/api".length); // keep leading slash
    const headers = {};
    // forward only the headers the ingress cares about; never inject a content-type
    if (req.headers["content-type"]) headers["content-type"] = req.headers["content-type"];
    if (req.headers["idempotency-key"]) headers["idempotency-key"] = req.headers["idempotency-key"];

    const proxyReq = httpRequest(
      { hostname: target.hostname, port: target.port || 80, path, method: req.method, headers },
      (proxyRes) => {
        res.writeHead(proxyRes.statusCode ?? 502, { "content-type": proxyRes.headers["content-type"] ?? "application/json" });
        proxyRes.pipe(res);
      }
    );
    proxyReq.on("error", (err) => {
      res.writeHead(502, { "content-type": "application/json" });
      res.end(JSON.stringify({ proxyError: String(err), hint: `Is restate-server reachable at ${INGRESS}?` }));
    });
    req.pipe(proxyReq);
    return;
  }

  // --- static files ---
  let urlPath = req.url === "/" ? "/index.html" : req.url.split("?")[0];
  try {
    const data = await readFile(join(PUBLIC, urlPath));
    res.writeHead(200, { "content-type": MIME[extname(urlPath)] ?? "application/octet-stream" });
    res.end(data);
  } catch {
    res.writeHead(404, { "content-type": "text/plain" });
    res.end("Not found");
  }
});

server.listen(PORT, () => {
  console.log(`Restate 101 demo UI -> http://localhost:${PORT}`);
  console.log(`Proxying /api/* -> ${INGRESS}`);
});
