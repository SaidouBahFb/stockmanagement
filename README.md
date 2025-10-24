# Stock Management - API REST

Application de gestion de stock développée avec **Spring Boot 3.5.6** et **Java 21**.


## 🛠️ Technologies utilisées

| Technologie | Version | Utilisation |
|-------------|---------|-------------|
| **Java** | 21 | Langage de programmation |
| **Spring Boot** | 3.5.6 | Framework backend |
| **Spring Data JPA** | 3.5.6 | ORM / Base de données |
| **MySQL** | 8.0 | Base de données |
| **Lombok** | - | Réduction du code boilerplate |
| **SpringDoc OpenAPI** | 2.5.0 | Documentation API (Swagger) |
| **Spring Actuator** | 3.5.6 | Monitoring et santé de l'app |
| **Docker** | - | Conteneurisation |
| **Kubernetes** | - | Orchestration |

---

## Prérequis

- **Java 21** installé
- **Maven** installé
- **Docker** et **Docker Compose** installés
- **Minikube** et **kubectl** (pour le déploiement Kubernetes)

---

## Démarrage Rapide

### **Option 1 : Avec Docker Compose**

```cmd
REM Construire et démarrer tous les services
docker-compose up -d --build

REM Vérifier que tout fonctionne
docker-compose ps
```

**Services disponibles :**
- **API REST** : http://localhost:8080
- **Swagger UI** : http://localhost:8080/swagger-ui.html
- **phpMyAdmin** : http://localhost:8081
- **MySQL** : localhost:3307

**Arrêter les services :**
```cmd
docker-compose down
```

---

### **Option 2 : Avec Kubernetes/Minikube **

Voir le guide complet dans le dossier **[k8s/README.md](k8s/README.md)**

**Démarrage rapide :**
```cmd
REM 1. Démarrer Minikube
minikube start --driver=docker

REM 2. Charger les images
docker-compose build
minikube image load stockmanagement-stockmanagement-app:latest
minikube ssh docker pull mysql:8.0
minikube ssh docker pull phpmyadmin/phpmyadmin

REM 3. Déployer
cd k8s
kubectl apply -k .

REM 4. Obtenir les URLs
minikube service stockmanagement-app-svc -n m2glisi --url
minikube service phpmyadmin-svc -n m2glisi --url
```

---

### **Documentation interactive**

Accédez à Swagger UI pour tester tous les endpoints :
- **Docker Compose** : http://localhost:8080/swagger-ui.html
- **Minikube** : http://127.0.0.1:xxxxx/swagger-ui.html (port obtenu via `minikube service`)
---

