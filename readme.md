# Ambiente de Desenvolvimento

```bash
SUBNET_CONFIG=10.0.0.0/24 \
POSTGRES_IPV4=10.0.0.10 \
POSTGRES_PORT=5432 \
POSTGRES_USER=postgres \
POSTGRES_PASSWORD=123456 \
POSTGRES_DB=markdowner-db \
PGADMIN_IPV4=10.0.0.15 \
PGADMIN_PORT=15432 \
PGADMIN_DEFAULT_EMAIL=admin@local.host \
PGADMIN_DEFAULT_PASSWORD=123456 \
SPRINGBOOT_IPV4=10.0.0.20 \
SPRINGBOOT_PORT=8080 \
DATASOURCE_USERNAME=$POSTGRES_USER \
DATASOURCE_PASSWORD=$POSTGRES_PASSWORD \
DATASOURCE_URL=jdbc:postgresql://$POSTGRES_IPV4:$POSTGRES_PORT/$POSTGRES_DB \
docker compose up -d
```

## Observando Logs do SpringBoot
```bash
docker attach springboot
```

### Notas
O DevTools está integrado a imagem docker, ou seja, atualizações em arquivos vão refletir na execução da imagem em tempo real.