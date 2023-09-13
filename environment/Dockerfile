FROM consul:1.15.3

RUN mkdir /tmp/init

COPY init/consul/* /tmp/init/consul/
RUN chmod 755 /tmp/init/consul/*
RUN dos2unix /tmp/init/consul/*

CMD /tmp/init/consul/start.sh