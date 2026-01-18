package com.example.evfinal_cuentas_programacion_ii.Medicion

import androidx.room.*
import kotlinx.coroutines.flow.Flow

// 1. Entidad: Define la estructura de la tabla en SQLite
@Entity(tableName = "mediciones")
data class Medicion(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tipo: String, // Agua, Luz o Gas
    val valor: Double,
    val fecha: String
)

// 2. DAO: Interfaz para las operaciones de la base de datos
@Dao
interface MedicionDao {
    @Query("SELECT * FROM mediciones ORDER BY id DESC")
    fun getAll(): Flow<List<Medicion>>

    @Insert
    suspend fun insert(medicion: Medicion)
}

// 3. Base de Datos: Punto de acceso principal
@Database(entities = [Medicion::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun medicionDao(): MedicionDao
}