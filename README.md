# Parcial primer corte AREP

## Arquitectura

<img width="726" height="256" alt="image" src="https://github.com/user-attachments/assets/96faac67-9bad-4fbe-9093-0533e400ebba" />


## Requerimientos Funcionales 

Rutas de Fachada: 

- /add?x=<real> → reenvía a BACKEND /add?x=<real>
- /list → reenvía a BACKEND /list
- /clear → reenvía a BACKEND /clear
- /stats → reenvía a BACKEND /stats
- /cliente → retorna el cliente HTML+js

### Requerimientos funcionando

- /add?x=<real> → reenvía a BACKEND /add?x=<real>
- /list → reenvía a BACKEND /list
- /clear → reenvía a BACKEND /clear
- /cliente → retorna el cliente HTML+js

## Ejecucion y uso 

```
mvn clean install
```

Arranque Facade server

```
 java -cp target/classes facade.Facade 
```

Arranque Backend Server
```
 java -cp target/classes backend.ReflexiveBackend
```

### Demo

<img width="413" height="185" alt="image" src="https://github.com/user-attachments/assets/ae37a737-97a7-4412-a4de-0c7c5a24a2d1" />

Agregar

<img width="421" height="201" alt="image" src="https://github.com/user-attachments/assets/e37bc22e-bc6f-443c-848c-b2d19a088302" />

Listar

<img width="398" height="226" alt="image" src="https://github.com/user-attachments/assets/bcbd8818-1495-40ed-98a5-0052efb11139" />

<img width="421" height="234" alt="image" src="https://github.com/user-attachments/assets/a4072b94-4338-4102-b394-0629822f658e" />

Limpiar





