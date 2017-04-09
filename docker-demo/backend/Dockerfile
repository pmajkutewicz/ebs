FROM openjdk:jre-alpine
RUN apk add --update wget bash && rm -f /var/cache/apk/*
RUN wget https://github.com/pmajkutewicz/ebs/releases/download/0.1.0/ebs-0.1.0.jar

COPY wait-for-it.sh ./wait-for-it.sh

EXPOSE 8083
CMD ["java", "-jar", "ebs-0.1.0.jar", "--db_port=3306", "--db_host=db"]
