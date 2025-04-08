"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.getProductServiceUrl = exports.registerWithEureka = void 0;
const eureka_js_client_1 = require("eureka-js-client");
const dotenv_1 = __importDefault(require("dotenv"));
dotenv_1.default.config();
const PORT = process.env.PORT || 9999;
const MYHOST = process.env.HOST || 'localhost';
const MYIP = process.env.IP || '127.0.0.1';
const HOST_EUREKA = process.env.HOSTEUREKA || 'localhost';
const PORT_EUREKA = process.env.PORTEUREKA || '8761';
const eurekaClient = new eureka_js_client_1.Eureka({
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
        host: HOST_EUREKA, // Host donde corre Eureka
        port: Number(PORT_EUREKA),
        servicePath: '/eureka/apps/',
    },
});
const registerWithEureka = () => {
    //eurekaClient.logger.level('debug');
    eurekaClient.start((error) => {
        if (error) {
            console.error('❌ Error registrando en Eureka:', error);
        }
        else {
            console.log('✅ Microservicio registrado en Eureka');
        }
    });
};
exports.registerWithEureka = registerWithEureka;
const getProductServiceUrl = () => {
    const instances = eurekaClient.getInstancesByAppId('microservice-product-catalog'); // en mayúsculas
    if (instances.length > 0) {
        const { hostName, port } = instances[0];
        // Validar si `port` es un objeto con la propiedad `$`
        const resolvedPort = typeof port === 'object' && '$' in port ? port.$ : port;
        // Validar que `resolvedPort` no sea undefined
        if (resolvedPort === undefined) {
            throw new Error("El puerto no está definido para la instancia de microservice-product-catalog");
        }
        return `http://${hostName}:${resolvedPort}`;
    }
    else {
        throw new Error("No se encontró instancia de microservice-product-catalog");
    }
};
exports.getProductServiceUrl = getProductServiceUrl;
