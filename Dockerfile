FROM centos:latest
RUN mkdir /data
RUN mkdir /data/www
COPY ./www /data/www
COPY mock.jar .
# RUN yum -y install yum-utils
RUN yum -y install epel-release
RUN yum -y install nginx
RUN rm -f /etc/nginx.conf
COPY nginx.conf /etc/nginx/
RUN yum -y install java-11-openjdk-devel
EXPOSE 80
CMD bash -c "/usr/sbin/nginx && echo Nginx is UP && java -jar mock.jar"
#CMD /usr/sbin/nginx

