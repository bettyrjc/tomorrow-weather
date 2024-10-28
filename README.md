# Saving your locations 🚀

## Stack
- Kotlin
- Kort
- Redis



## Project Requirements:
Before use this repository you need to have installed redis on your machine

## Setup project
1. `git clone https://github.com/bettyrjc/combat-pokemon.git`
2. Build the project: `./gradle build`
3. run in your local redis
4. Run the project: `./gradle run`

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
