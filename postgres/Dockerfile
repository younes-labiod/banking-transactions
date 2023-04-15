FROM postgres
COPY init.sql /docker-entrypoint-initdb.d/
ENV POSTGRES_PASSWORD mysecretpassword
EXPOSE 5432