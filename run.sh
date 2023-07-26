export POSTGRES_HOST=localhost
export POSTGRES_PORT=5432
export POSTGRES_USER=postgres
export POSTGRES_DB=postgres
export POSTGRES_PASSWORD=password
export KTOR_LOG_LEVEL=TRACE
./gradlew nativeMainBinaries
./build/bin/native/releaseExecutable/ktor-native.kexe
