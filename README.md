# Doc Readme
## Project Requirement
- Spring Boot 3.0.0
- Java 17
- Maven 3.8.6
- MySQL 8.0.30

## Quick Start
`./mvnw spring-boot:run`

## Postman Documentation
- [POSTMAN](https://documenter.getpostman.com/view/33287012/2sA3s7iob2)

## Base URL

```
http://localhost:8888
```

## Authentication

### Sign In

- **URL:** `/api/auth/signin`
- **Method:** `POST`
- **Body:**
  ```json
  {
    "email": "",
    "password": ""
  }
  ```

### Sign Up

- **URL:** `/api/auth/signup`
- **Method:** `POST`
- **Body:**
  ```json
  {
    "username": "",
    "email": "",
    "password": ""
  }
  ```

### Admin Creation

- **URL:** `/api/auth/admin`
- **Method:** `POST`
- **Headers:**
  ```http
  X-Admin-Creation: nurdiansyahmyadminsecretcreation
  ```
- **Body:**
  ```json
  {
    "username": "",
    "email": "",
    "password": ""
  }
  ```

## User Management

### Change User Role

- **URL:** `/api/users/role/:id`
- **Method:** `PUT`
- **Headers:**
  ```http
  Authorization: Bearer {token}
  ```
- **Body:**
  ```json
  {
    "role": ""
  }
  ```

### Get All Users

- **URL:** `/api/users`
- **Method:** `GET`
- **Headers:**
  ```http
  Authorization: Bearer {token}
  ```

### Delete User

- **URL:** `/api/users/:id`
- **Method:** `DELETE`
- **Headers:**
  ```http
  Authorization: Bearer {token}
  ```

### Get Current User

- **URL:** `/api/user`
- **Method:** `GET`
- **Headers:**
  ```http
  Authorization: Bearer {token}
  ```

### Update User

- **URL:** `/api/user`
- **Method:** `PUT`
- **Headers:**
  ```http
  Authorization: Bearer {token}
  ```
- **Body:**
  ```json
  {
    "username": "",
    "email": ""
  }
  ```

### Update Password

- **URL:** `/api/user/password`
- **Method:** `PUT`
- **Headers:**
  ```http
  Authorization: Bearer {token}
  ```
- **Body:**
  ```json
  {
    "old_password": "",
    "new_password": ""
  }
  ```

## Places

### Get All Places

- **URL:** `/api/places`
- **Method:** `GET`
- **Query Parameters:**
    - `search`
    - `limit`
    - `popular`

### Get One Place

- **URL:** `/api/place/:id`
- **Method:** `GET`

## Posts

### Get All Public Posts

- **URL:** `/api/public/posts`
- **Method:** `GET`
- **Query Parameters:**
    - `search`
    - `limit`

### Get All User Posts

- **URL:** `/api/posts`
- **Method:** `GET`
- **Headers:**
  ```http
  Authorization: Bearer {token}
  ```
- **Query Parameters:**
    - `search`
    - `limit`

### Get One Post

- **URL:** `/api/posts/:id`
- **Method:** `GET`
- **Headers:**
  ```http
  Authorization: Bearer {token}
  ```

### Create Post

- **URL:** `/api/posts`
- **Method:** `POST`
- **Headers:**
  ```http
  Authorization: Bearer {token}
  ```
- **Body:**
  ```json
  {
    "place_id": "",
    "title": "",
    "description": "",
    "picture": "",
    "rating": ""
  }
  ```

### Update Post

- **URL:** `/api/posts/:id`
- **Method:** `PUT`
- **Headers:**
  ```http
  Authorization: Bearer {token}
  ```
- **Body:**
  ```json
  {
    "place_id": "",
    "title": "",
    "description": "",
    "picture": "",
    "rating": ""
  }
  ```

### Delete Post

- **URL:** `/api/posts/:id`
- **Method:** `DELETE`
- **Headers:**
  ```http
  Authorization: Bearer {token}
  ```
