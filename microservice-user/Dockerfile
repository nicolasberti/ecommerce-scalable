FROM node:18

WORKDIR /app

COPY package*.json ./

RUN npm install
RUN npm install -g typescript

COPY . .

RUN npm run build

EXPOSE 8082

CMD ["npm", "start"]
