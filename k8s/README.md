# 🚀 Déploiement Kubernetes - Stock Management

Application de gestion de stock déployée sur **Minikube**.

---

## 📋 Prérequis

- **Minikube** installé
- **kubectl** installé
- **Docker** installé

---

## 🎯 Déploiement Rapide (Étape par Étape)

### **1️⃣ Construire l'image Docker**

```cmd
cd C:\Users\saidou.bah\Desktop\cours\webservice\stockmanagement
docker-compose build
```

### **2️⃣ Démarrer Minikube**

```cmd
minikube start --driver=docker
```

### **3️⃣ Charger les images dans Minikube**

```cmd
REM Charger votre image locale
minikube image load stockmanagement-stockmanagement-app:latest

REM Télécharger MySQL
minikube ssh docker pull mysql:8.0

REM Télécharger phpMyAdmin
minikube ssh docker pull phpmyadmin/phpmyadmin
```

### **4️⃣ Vérifier que les images sont chargées**

```cmd
minikube image ls | findstr -i "mysql stockmanagement phpmyadmin"
```

### **5️⃣ Déployer l'application**

```cmd
cd k8s
kubectl apply -k .
```

### **6️⃣ Surveiller le déploiement**

```cmd
kubectl get pods -n m2glisi -w
```

**Attendez que tous les pods soient "Running" avec "1/1" dans la colonne READY.**

Appuyez sur **Ctrl+C** pour arrêter la surveillance.

### **7️⃣ Obtenir les URLs d'accès**

```cmd
REM URL de l'API
minikube service stockmanagement-app-svc -n m2glisi --url

REM URL phpMyAdmin
minikube service phpmyadmin-svc -n m2glisi --url
```

---

## 🌐 Accéder aux services

Une fois déployé, utilisez les URLs fournies par la commande ci-dessus.

**Exemple d'URLs** (les ports peuvent varier) :
- **API REST** : `http://127.0.0.1:xxxxx`
- **phpMyAdmin** : `http://127.0.0.1:xxxxx`
- **Swagger UI** : `http://127.0.0.1:xxxxx/swagger-ui.html`

---

## 📝 Tester l'API

```cmd
REM Lister les marques
curl http://127.0.0.1:xxxxx/marques

REM Ajouter une marque
curl -X POST http://127.0.0.1:xxxxx/marques -H "Content-Type: application/json" -d "{\"nom\":\"Nike\",\"description\":\"Marque de sport\"}"
```

*(Remplacez `xxxxx` par le port affiché par la commande `minikube service`)*

---

## 🔍 Commandes Utiles

### **Voir tous les pods**
```cmd
kubectl get pods -n m2glisi
```

### **Voir tous les services**
```cmd
kubectl get svc -n m2glisi
```

### **Voir les logs d'un pod**
```cmd
kubectl logs <nom-du-pod> -n m2glisi
```

### **Voir les détails d'un pod**
```cmd
kubectl describe pod <nom-du-pod> -n m2glisi
```

### **Redémarrer l'application**
```cmd
kubectl rollout restart deployment/stockmanagement-app -n m2glisi
```

---

## 🛑 Arrêter et Supprimer

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

## 🐛 Dépannage

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

## 📁 Structure des fichiers

```
k8s/
├── namespace.yaml              # Namespace m2glisi
├── mysql-secrets.yaml          # Mot de passe MySQL
├── mysql-pvc.yaml              # Stockage MySQL
├── mysql-deployment.yaml       # Base de données
├── mysql-service.yaml          # Service MySQL
├── app-configmap.yaml          # Configuration app
├── app-deployment.yaml         # Application (2 replicas)
├── app-service.yaml            # Service app (NodePort)
├── phpmyadmin-deployment.yaml  # Interface MySQL
├── phpmyadmin-service.yaml     # Service phpMyAdmin
├── kustomization.yaml          # Déploiement global
└── README.md                   # Ce fichier
```

---

## ✅ Checklist

- [ ] Minikube démarré (`minikube start`)
- [ ] Images chargées dans Minikube
- [ ] Déploiement appliqué (`kubectl apply -k .`)
- [ ] Pods en état "Running" (`kubectl get pods -n m2glisi`)
- [ ] URL obtenue (`minikube service ... --url`)
- [ ] API testée avec curl ou Postman
- [ ] phpMyAdmin accessible

---

## 🎓 Composants déployés

| Composant | Type | Replicas | Port |
|-----------|------|----------|------|
| **MySQL** | Deployment | 1 | 3306 |
| **Application** | Deployment | 2 | 8080 |
| **phpMyAdmin** | Deployment | 1 | 80 |

---

**Bon déploiement ! 🚀**
