#!/bin/bash
# Interrupt script if any of the commands returns a non-0 code.
set -eu

docker build -t shaz900/stock-ticker-service .

echo "Pushing image"
docker push shaz900/stock-ticker-service