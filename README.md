#### Steps to reproduce:

1) Run `./gradlew macosX64Test` to check compilation and run works fine (choose your target if it's differ from macosX64 I use).

2) Run `./gradlew publish` to get compile time exception:

```
Sqlite3CApi.native.kt:34:157 type kotlin.Any?  is not supported here: doesn't correspond to any C type
```

#### More

After line `src/nativeTest/kotlin/integration/ExecuteTest.kt:29` is uncommented, test itself stopped working (`./gradlew macosX64Test`).