# Changelog

Registro de cambios y versiones del proyecto Star Wars Wiki.

## [2.0.0] - Entrega Tarea 4 (Aplicación Multifuncional)
### Añadido
- **Persistencia (Room):** Implementación completa de base de datos local, sustituyendo el repositorio Mock.
- **Navigation Drawer:** Menú lateral funcional para navegar entre Inicio, Listado, Ajustes y About Us.
- **Validación de Negocio:** Comprobación en ViewModel para evitar nombres duplicados antes de insertar.
- **Notificaciones:** Sistema de notificaciones locales (Channel ID) tras crear una especie exitosamente.
- **Filtrado y Orden:** Opción en TopAppBar para ordenar la lista alfabéticamente (ASC/DESC).
- **Animaciones:** Transiciones suaves entre pantallas y animaciones de entrada en la lista.
- **Permisos:** Gestión de permisos en tiempo de ejecución para Android 13+ (POST_NOTIFICATIONS).

### Cambiado
- Refactorización de `SpeciesRepository` para usar `SpeciesDAO` y devolver `Flow`.
- Actualización de `AddSpecieScreen` para gestionar la navegación basada en eventos de éxito/error.

## [1.0.0] - Entrega Tarea 3 (MVVM & Hilt)
### Añadido
- Estructura base del proyecto con arquitectura MVVM.
- Configuración de Dagger Hilt (@HiltViewModel, @AndroidEntryPoint).
- Pantallas principales: Listado, Formulario (Alta/Edición) y Detalle.
- Repositorio Fake (Mock) para simulación de datos.
- Pantalla "About Us" con información del desarrollador.