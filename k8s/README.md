# Déploiement Kubernetes - Stock Management

Application de gestion de stock déployée sur **Minikube**.

---

## Prérequis

- **Minikube** installé
- **kubectl** installé
- **Docker** installé

---

## Déploiement Rapide (Étape par Étape)

### **1️ Construire l'image Docker**

```cmd
cd C:\Users\saidou.bah\Desktop\cours\webservice\stockmanagement
docker-compose build
```

### **2 Démarrer Minikube**

```cmd
minikube start --driver=docker
```

### **3 Charger les images dans Minikube**

```cmd
REM Charger votre image locale
minikube image load stockmanagement-stockmanagement-app:latest

REM Télécharger MySQL
minikube ssh docker pull mysql:8.0

REM Télécharger phpMyAdmin
minikube ssh docker pull phpmyadmin/phpmyadmin
```

### **4️ Vérifier que les images sont chargées**

```cmd
minikube image ls | findstr -i "mysql stockmanagement phpmyadmin"
```

### **5️ Déployer l'application**

```cmd
cd k8s
kubectl apply -k .
```

### **6️ Surveiller le déploiement**

```cmd
kubectl get pods -n m2glisi -w
```

**Attendez que tous les pods soient "Running" avec "1/1" dans la colonne READY.**

Appuyez sur **Ctrl+C** pour arrêter la surveillance.

### **7️ Obtenir les URLs d'accès**

```cmd
REM URL de l'API
minikube service stockmanagement-app-svc -n m2glisi --url

REM URL phpMyAdmin
minikube service phpmyadmin-svc -n m2glisi --url
```

---

## Accéder aux services

Une fois déployé, utilisez les URLs fournies par la commande ci-dessus.

**Exemple d'URLs** (les ports peuvent varier) :
- **API REST** : `http://127.0.0.1:xxxxx`
- **phpMyAdmin** : `http://127.0.0.1:xxxxx`
- **Swagger UI** : `http://127.0.0.1:xxxxx/swagger-ui.html`

---

## Tester l'API

```cmd
REM Lister les marques
curl http://127.0.0.1:xxxxx/marques

REM Ajouter une marque
curl -X POST http://127.0.0.1:xxxxx/marques -H "Content-Type: application/json" -d "{\"nom\":\"Nike\",\"description\":\"Marque de sport\"}"
```

*(Remplacez `xxxxx` par le port affiché par la commande `minikube service`)*

---

##  Redéploiement après modifications

### **1️ Modification d'un ConfigMap (app-configmap.yaml)**
```cmd
REM Appliquer les changements
kubectl apply -f app-configmap.yaml

REM Redémarrer les pods pour prendre en compte les nouvelles valeurs
kubectl rollout restart deployment/stockmanagement-app -n m2glisi
```

### **2️ Modification d'un Secret (mysql-secrets.yaml)**
```cmd
REM Appliquer les changements
kubectl apply -f mysql-secrets.yaml

REM Redémarrer les déploiements concernés
kubectl rollout restart deployment/mysql -n m2glisi
kubectl rollout restart deployment/stockmanagement-app -n m2glisi
kubectl rollout restart deployment/phpmyadmin -n m2glisi
```

### **3 Modification d'un Deployment (app-deployment.yaml, mysql-deployment.yaml, etc.)**
```cmd
REM Kubernetes met à jour automatiquement les pods
kubectl apply -f app-deployment.yaml

REM Vérifier le statut du rollout
kubectl rollout status deployment/stockmanagement-app -n m2glisi
```

### **4️ Modification d'un Service (app-service.yaml, mysql-service.yaml, etc.)**
```cmd
REM Kubernetes met à jour le service immédiatement
kubectl apply -f app-service.yaml
```

### **5️ Modification du code source (nouvelle image Docker)**
```cmd
REM 1. Rebuild l'image Docker
cd ..
docker-compose build

REM 2. Charger la nouvelle image dans minikube
minikube image load stockmanagement-stockmanagement-app:latest

REM 3. Forcer le redémarrage des pods pour utiliser la nouvelle image
kubectl rollout restart deployment/stockmanagement-app -n m2glisi

REM 4. Vérifier que les nouveaux pods démarrent
kubectl get pods -n m2glisi -w
```

### **6️ Redéployer TOUT le projet (après plusieurs modifications)**
```cmd
REM Depuis le dossier k8s
kubectl apply -k .

REM Redémarrer tous les déploiements si nécessaire
kubectl rollout restart deployment --all -n m2glisi
```

---

## Commandes Utiles

### **Voir tous les pods**
```cmd
kubectl get pods -n m2glisi
kubectl get pods -n m2glisi -w  REM Mode watch (actualisation en temps réel)
```

### **Voir tous les services**
```cmd
kubectl get svc -n m2glisi
```

### **Voir les logs d'un pod**
```cmd
kubectl logs <nom-du-pod> -n m2glisi
kubectl logs -f <nom-du-pod> -n m2glisi  REM Mode follow (temps réel)
```

### **Voir les détails d'un pod**
```cmd
kubectl describe pod <nom-du-pod> -n m2glisi
```

### **Vérifier les déploiements**
```cmd
kubectl get deployments -n m2glisi
```

### **Vérifier les ConfigMaps et Secrets**
```cmd
kubectl get configmaps -n m2glisi
kubectl get secrets -n m2glisi
```

### **Accéder à un conteneur (debug)**
```cmd
kubectl exec -it <nom-du-pod> -n m2glisi -- /bin/sh
```

### **Redémarrer l'application**
```cmd
kubectl rollout restart deployment/stockmanagement-app -n m2glisi
```

### **Voir l'historique des déploiements**
```cmd
kubectl rollout history deployment/stockmanagement-app -n m2glisi
```

### **Revenir à la version précédente (rollback)**
```cmd
kubectl rollout undo deployment/stockmanagement-app -n m2glisi
```

---

## Arrêt et redémarrage de Minikube

### **Arrêter Minikube (sans perdre les données)**

```cmd
minikube stop
```

 Cette commande arrête le cluster Kubernetes mais **conserve toutes les ressources** (pods, services, volumes, ConfigMaps, Secrets).

### **Redémarrer Minikube**

```cmd
REM 1. Démarrer Minikube
minikube start --driver=docker

REM 2. Recharger votre image locale (recommandé avec imagePullPolicy: Never)
minikube image load stockmanagement-stockmanagement-app:latest

REM 3. Vérifier que tout redémarre automatiquement
kubectl get pods -n m2glisi -w
```

###  **Ce qui se passe automatiquement après `minikube start` :**

-  Tous les **Deployments** redémarrent automatiquement
-  Tous les **Services** sont recréés
-  Les **ConfigMaps** et **Secrets** sont restaurés
-  Les **données MySQL** sont conservées (PersistentVolume)
-  **Aucune action manuelle nécessaire** (sauf recharger l'image)

###  **Point important : Images Docker locales**

Si vous utilisez `imagePullPolicy: Never`, rechargez toujours votre image après `minikube start` :

```cmd
minikube image load stockmanagement-stockmanagement-app:latest
```

###  **Vérifier que tout fonctionne**

```cmd
REM Voir l'état des pods
kubectl get pods -n m2glisi

REM Voir les services
kubectl get svc -n m2glisi

REM Obtenir les URLs
minikube service stockmanagement-app-svc -n m2glisi --url
minikube service phpmyadmin-svc -n m2glisi --url
```

###  **Supprimer complètement Minikube (tout effacer)**

 **ATTENTION : Cette commande supprime TOUT (cluster + données) !**

```cmd
minikube delete
```

Après `minikube delete`, vous devrez tout redéployer depuis zéro :

```cmd
REM 1. Créer un nouveau cluster
minikube start

REM 2. Charger les images
minikube image load stockmanagement-stockmanagement-app:latest
minikube ssh docker pull mysql:8.0
minikube ssh docker pull phpmyadmin/phpmyadmin

REM 3. Redéployer tout
cd k8s
kubectl apply -k .
```

---

## Arrêter et Supprimer

### **Arrêter tous les pods (sans supprimer)**
```cmd
kubectl delete -k .
```

### **Supprimer complètement le namespace**
```cmd
kubectl delete namespace m2glisi
```

### **Arrêter Minikube**
```cmd
minikube stop
```

### **Supprimer Minikube (tout effacer)**
```cmd
minikube delete
```

---

## Dépannage

### **Les pods ne démarrent pas ?**

```cmd
REM Voir les événements
kubectl get events -n m2glisi --sort-by='.lastTimestamp'

REM Voir les détails d'un pod
kubectl describe pod <nom-du-pod> -n m2glisi
```

### **Erreur "ImagePullBackOff" ?**

L'image n'est pas dans Minikube. Rechargez-la :

```cmd
minikube image load stockmanagement-stockmanagement-app:latest
kubectl rollout restart deployment/stockmanagement-app -n m2glisi
```

### **L'app ne se connecte pas à MySQL ?**

Vérifiez que MySQL est bien démarré :

```cmd
kubectl get pods -n m2glisi
kubectl logs -l app=mysql -n m2glisi
```

---

## Points importants

1. **ConfigMap/Secret** : Les pods ne rechargent PAS automatiquement les nouvelles valeurs → il faut faire `kubectl rollout restart`

2. **Image Docker** : Si vous utilisez `imagePullPolicy: Never`, l'image doit être chargée dans minikube avec `minikube image load`

3. **PersistentVolume** : Les données MySQL sont conservées même après suppression des pods (tant que le PVC existe)

4. **Namespace** : Toujours spécifier `-n m2glisi` ou utiliser `kubectl config set-context --current --namespace=m2glisi`

---

##  Script complet de redéploiement rapide

```cmd
REM Depuis la racine du projet
cd C:\Users\saidou.bah\Desktop\cours\webservice\stockmanagement
docker-compose build
minikube image load stockmanagement-stockmanagement-app:latest
kubectl apply -k k8s/
kubectl rollout restart deployment --all -n m2glisi
kubectl get pods -n m2glisi -w
```

---

## Checklist

- [ ] Minikube démarré (`minikube start`)
- [ ] Images chargées dans Minikube
- [ ] Déploiement appliqué (`kubectl apply -k .`)
- [ ] Pods en état "Running" (`kubectl get pods -n m2glisi`)
- [ ] URL obtenue (`minikube service ... --url`)
- [ ] API testée avec curl ou Postman
- [ ] phpMyAdmin accessible

---
