#!/usr/bin/env bash
# Start the whole Restate 101 demo stack in Docker and register the service.
#   ./scripts/start.sh
# Re-running is safe (idempotent registration). Restate runs via Docker, so this
# does NOT depend on any restate-server / JDK / Node installed on the machine.
set -euo pipefail

cd "$(dirname "$0")/.."

ADMIN=http://localhost:9070
INGRESS=http://localhost:8080
DEMO=http://localhost:9080
UI=http://localhost:8088

echo "==> Building images and starting containers..."
docker compose up -d --build

# --- wait for the Restate admin API ---
echo -n "==> Waiting for Restate admin ($ADMIN)"
for i in $(seq 1 60); do
  if curl -fsS "$ADMIN/health" >/dev/null 2>&1; then echo " ✓"; break; fi
  echo -n "."; sleep 1
  [ "$i" = 60 ] && { echo " timed out"; docker compose logs restate | tail -30; exit 1; }
done

# --- wait for the Java service endpoint ---
echo -n "==> Waiting for demo endpoint ($DEMO)"
for i in $(seq 1 60); do
  if curl -fsS -o /dev/null "$DEMO/discover" -H 'accept: application/vnd.restate.endpointmanifest.v1+json' 2>/dev/null; then echo " ✓"; break; fi
  echo -n "."; sleep 1
  [ "$i" = 60 ] && { echo " timed out"; docker compose logs demo | tail -30; exit 1; }
done

# --- register the deployment (restate reaches the demo over the compose network) ---
echo "==> Registering deployment http://demo:9080"
curl -fsS "$ADMIN/deployments" \
  -H 'content-type: application/json' \
  -d '{"uri":"http://demo:9080","force":true}' >/dev/null
echo "    registered: $(curl -fsS "$INGRESS/restate/health")"

cat <<EOF

==> Up and running (all in Docker):
    Demo UI         $UI
    Restate Web UI  $ADMIN/ui   (per-step journals)
    Ingress         $INGRESS
    Demo endpoint   $DEMO

    Stop with:  ./scripts/stop.sh
EOF

# open the UI on macOS if available
command -v open >/dev/null 2>&1 && open "$UI" || true
