# Saving your locations 🚀

## Stack
- Kotlin
- Kort
- Redis



## Project Requirements:
Before use this repository you need to have installed redis on your machine

## Setup project
1. `git clone https://github.com/bettyrjc/combat-pokemon.git`
2. create .env file a put your tomorrow.io API KEY, see example in .env.example
3. Build the project: `./gradle build`
4. run in your local redis
5. Run the project: `./gradle run`

## API Interactions

### Get weather

Currently, we only support the next cities:
- Santiago (CL)
- Zúrich (CH)
- Auckland (NZ)
- Sídney (AU)
- Londres (UK)
- Georgia (USA)

```bash
curl "http://localhost:8080/weather?cityName=Santiago (CL)"
```



## Improvements
- [ ] Add test
- [ ] Config docker
- [ ] Add openAPI
- [ ] Implement something like fakerJs for kotlin
