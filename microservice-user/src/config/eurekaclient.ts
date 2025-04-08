import { Eureka } from 'eureka-js-client';
import dotenv from 'dotenv';

dotenv.config();

const PORT = process.env.PORT || 8082;
const MYHOST = process.env.HOST || 'microservice-user'
const MYIP = process.env.IP || '127.0.0.1'
const HOST_EUREKA = process.env.HOSTEUREKA || 'eureka'
const PORT_EUREKA = process.env.PORTEUREKA || '8761'

const eurekaClient = new Eureka({
  instance: {
    app: 'MICROSERVICE-USER', // Nombre del microservicio
    hostName: MYHOST, // O la IP de tu contenedor si estás en Docker
    ipAddr: MYIP,
    port: {
      '$': Number(PORT), // Puerto donde corre tu microservicio
      '@enabled': true,
    },
    vipAddress: 'microservice-user',
    dataCenterInfo: {
      '@class': 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo',
      name: 'MyOwn',
    },
  },
  eureka: {
    host: HOST_EUREKA,
    port: Number(PORT_EUREKA),
    servicePath: '/eureka/apps/',
    maxRetries: 5, // Número máximo de reintentos
    requestRetryDelay: 30000, // Retraso entre reintentos (en milisegundos)
  },
});

export const registerWithEureka = () => {
  //eurekaClient.logger.level('debug');
  eurekaClient.start((error) => {
    if (error) {
      console.error('❌ Error registrando en Eureka:', error);
    } else {
      console.log('✅ Microservicio registrado en Eureka');
    }
  });
};

export const getProductServiceUrl = () => {
  const instances = eurekaClient.getInstancesByAppId('MICROSERVICE-PRODUCT-CATALOG'); // en mayúsculas
  
  if (instances.length > 0) {
    const { ipAddr, port } = instances[0];

    // Validar si `port` es un objeto con la propiedad `$`
    const resolvedPort = typeof port === 'object' && '$' in port ? port.$ : port;

    // Validar que `resolvedPort` no sea undefined
    if (resolvedPort === undefined) {
      throw new Error("El puerto no está definido para la instancia de microservice-product-catalog");
    }

    return `http://${ipAddr}:${resolvedPort}`;
  } else {
    throw new Error("No se encontró instancia de microservice-product-catalog");
  }
};