FROM mariadb:10
LABEL maintainer="sebashh26@gmail.com"

ENV MARIADB_ROOT_PASSWORD=toor
ENV MARIADB_DATABASE=librarybd
ENV MARIADB_ROOT_HOST="%"

#COPY ./scripts/ /docker-entrypoint-initdb.d/

# RUN ls -al /docker-entrypoint-initdb.d/ && sleep 5s

CMD ["--default-authentication-plugin=mysql_native_password"]