#!/bin/bash

set -e

echo "================================="
echo "🚀 Deploy SupremeNetworkApi"
echo "================================="

echo "🐳 Rebuild e restart..."

docker compose up -d --build

echo "✅ Deploy concluído!"

docker ps --filter "name=api_supreme_network"
