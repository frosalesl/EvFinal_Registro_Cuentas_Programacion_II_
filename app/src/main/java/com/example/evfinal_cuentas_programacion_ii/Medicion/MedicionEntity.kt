package com.example.evfinal_cuentas_programacion_ii.Medicion

import androidx.room.*
import kotlinx.coroutines.flow.Flow

// Entidad donde se define la estructura de la tabla en SQLite
// Representa PAO solicitado aplicado a la persistencia
@Entity(tableName = "mediciones")
data class Medicion(
    // Clave primaria autoincremental para identificar de forma única cada registro
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tipo: String, // Almacena el tipo de servicio (Agua, Luz o Gas)
    val valor: Double, // Almacena el valor numérico capturado por el medidor
    val fecha: String // Almacena la fecha del registro en formato String
)

// Define las operaciones CRUD
@Dao
interface MedicionDao {

    // Consulta para obtener todos los registros. Se utiliza Flow para que la lista sea reactiva y se actualice automáticamente en la UI
    @Query("SELECT * FROM mediciones ORDER BY id DESC")
    fun getAll(): Flow<List<Medicion>>

    // Operación de inserción donde se marca como 'suspend' para ser ejecutada dentro de una corrutina sin bloquear el hilo principal
    @Insert
    suspend fun insert(medicion: Medicion)

    // Operación de eliminación que permite borrar un registro específico basándose en su ID
    @Delete
    suspend fun delete(medicion: Medicion)
}

// Acceso principal a la persistencia. Define la configuración de la base de datos y la versión del esquema.
@Database(entities = [Medicion::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    // Función abstracta que proporciona acceso al DAO
    abstract fun medicionDao(): MedicionDao
}