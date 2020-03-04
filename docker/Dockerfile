FROM centos:7
ENV JAVA_HOME /usr/lib/jvm/jre
ENV CATALINA_HOME /service

ADD . /service
WORKDIR /service
RUN bash docker-env-config.sh

EXPOSE 8486
USER onap
WORKDIR /service
ENTRYPOINT /service/docker-entrypoint.sh
