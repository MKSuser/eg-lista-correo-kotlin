package ar.edu.unsam.algo2.listaCorreo

data class Post(
    val emisor: Usuario,
    val asunto: String,
    val mensaje: String)
{
    fun mailEmisor() = emisor.mailPrincipal

    fun contiene(palabra: String): Boolean{
        return mensaje.contains(palabra) || asunto.contains(palabra)
    }

    fun envioMail() { emisor.envioMail() }

    fun estadoEmisor() = emisor.bloqueado

}