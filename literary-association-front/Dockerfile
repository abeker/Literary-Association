# FROM node:12.7-alpine AS build
# WORKDIR /usr/src/app
# COPY package.json package-lock.json ./
# RUN npm install
# COPY . .
# RUN npm run build

FROM node:12.2.0
WORKDIR /app
COPY package.json /app/package.json
RUN npm install
RUN npm install -g @angular/cli 
COPY . /app
EXPOSE 4200 
CMD ng serve --host 0.0.0.0

# FROM node:12.2.0
# WORKDIR /app
# COPY package.json /app/package.json
# RUN npm install
# RUN npm install -g @angular/cli@6.0.8
# COPY . /app
# EXPOSE 4200
# CMD ng serve --host 0.0.0.0
