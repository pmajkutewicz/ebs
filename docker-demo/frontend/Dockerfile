FROM nginx
ADD https://github.com/pmajkutewicz/ebs/releases/download/0.1.0/frontend-0.1.0-resources.tar.gz /usr/share/nginx/html
WORKDIR /usr/share/nginx/html
RUN tar xzf frontend-0.1.0-resources.tar.gz && rm frontend-0.1.0-resources.tar.gz

EXPOSE 80
