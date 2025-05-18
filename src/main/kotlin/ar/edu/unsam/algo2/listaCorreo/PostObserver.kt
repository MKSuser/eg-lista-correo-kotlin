package ar.edu.unsam.algo2.listaCorreo

interface PostObserver {
    fun postEnviado(post: Post, listaCorreo: ListaCorreo)

}
/*************************************************************************************/
class MailObserver: PostObserver{
    lateinit var mailSender: MailSender
    var prefijo = ""

    override fun postEnviado(post: Post, listaCorreo: ListaCorreo) {
        mailSender.sendMail(
            Mail(from = post.mailEmisor(),
                to = mailsDestino(listaCorreo, post),
                subject = "[${prefijo}] ${post.asunto}",
                content = post.mensaje)
        )
    }

    private fun mailsDestino(listaCorreo: ListaCorreo, post: Post): String =
        listaCorreo.getUsuariosDestino(post).joinToString(", ") { it.mailPrincipal }

}
/*************************************************************************************/
class MalasPalabrasObserver: PostObserver{
    var malasPalabras = mutableListOf<String>("guerra", "hambre")
    var postARevisar = mutableListOf<Post>()

    override fun postEnviado(post: Post, listaCorreo: ListaCorreo) {
        if (this.tieneMalasPalabras(post)){
            postARevisar.add(post)
        }
    }

    private fun tieneMalasPalabras(post: Post): Boolean {
        return malasPalabras.any{ post.contiene(it) }
    }

    fun agregarMalaPalabra(palabra:String){
        malasPalabras.add(palabra)
    }

}
/*************************************************************************************/
class UsuarioVerbosoObserver: PostObserver{
    val topeMails: Int = 5

    override fun postEnviado(post: Post, listaCorreo: ListaCorreo): Unit {
        post.envioMail()

        val emisor = post.emisor

        if (emisor.usuarioEnvioMuchosMails(this)){
            emisor.bloquear()
        }

    }
}