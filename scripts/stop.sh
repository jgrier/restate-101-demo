#!/usr/bin/env bash
# Tear down the Restate 101 demo stack.
#   ./scripts/stop.sh           stop & remove containers (keeps state in the volume)
#   ./scripts/stop.sh --clean   also delete the volume (fresh state next start)
set -euo pipefail

cd "$(dirname "$0")/.."

if [ "${1:-}" = "--clean" ]; then
  echo "==> Stopping and removing containers + data volume..."
  docker compose down --volumes
else
  echo "==> Stopping and removing containers (state volume kept)..."
  docker compose down
fi

echo "==> Done. Standard ports (8080/9070/9080/8088) are free."
