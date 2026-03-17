FROM postgres:17.8-alpine3.23

ENV POSTGRES_DB=ricknmorty
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=postgres

COPY data.sql /docker-entrypoint-initdb.d/data.sql

EXPOSE 5432
