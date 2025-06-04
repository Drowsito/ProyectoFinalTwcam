#!/bin/bash

# Crear el namespace proyectofinal (si ya existe, no hace nada)
echo "Creando el namespace 'proyectofinal'..."
kubectl create namespace proyectofinal 2>/dev/null || true

echo "Desplegando las bases de datos en 'proyectofinal'..."
kubectl apply -n proyectofinal -f ./mysql/
kubectl apply -n proyectofinal -f ./mongo/

echo "Esperando a que MySQL esté listo en 'proyectofinal'..."
kubectl wait -n proyectofinal --for=condition=ready pod -l app=mysql --timeout=90s

echo "Esperando a que Mongo esté listo en 'proyectofinal'..."
kubectl wait -n proyectofinal --for=condition=ready pod -l app=mongo --timeout=90s

echo "Desplegando el config-server en 'proyectofinal'..."
kubectl apply -n proyectofinal -f ./config-server/

echo "Esperando a que config-server esté listo en 'proyectofinal'..."
kubectl wait -n proyectofinal --for=condition=ready pod -l app=config-server --timeout=90s

echo "Desplegando la API bicicletas en 'proyectofinal'..."
kubectl apply -n proyectofinal -f ./bicicletas/

echo "Desplegando la API polucion en 'proyectofinal'..."
kubectl apply -n proyectofinal -f ./polucion/

echo "Desplegando la API ayuntamiento en 'proyectofinal'..."
kubectl apply -n proyectofinal -f ./ayuntamiento/

echo "Esperando a que las APIs estén listas en 'proyectofinal'..."
kubectl wait -n proyectofinal --for=condition=ready pod -l app=bicicletas --timeout=90s
kubectl wait -n proyectofinal --for=condition=ready pod -l app=polucion --timeout=90s
kubectl wait -n proyectofinal --for=condition=ready pod -l app=ayuntamiento --timeout=90s

echo "Desplegando los ingress en 'proyectofinal'..."
kubectl apply -n proyectofinal -f ./ingress/

# Si necesitas hacer port-forward sobre el ingress-controller (instalado en el namespace de ingress-nginx),
# la siguiente línea no cambiaría de namespace, pues el controlador suele estar en 'ingress-nginx'.
#kubectl port-forward -n ingress-nginx svc/ingress-nginx-controller 8080:80

echo "¡Despliegue completo en el namespace 'proyectofinal'!"
