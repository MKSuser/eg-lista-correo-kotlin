package ar.edu.unsam.algo2.listaCorreo

interface PostObserver {
    fun postRecibido(post: Post,listaCorreo: ListaCorreo)

}

class MailObserver: PostObserver{
    lateinit var mailSender: MailSender
    var prefijo = ""

    override fun postRecibido(post: Post, listaCorreo: ListaCorreo) {
        mailSender.sendMail(
            Mail(from = post.mailEmisor(),
                to = mailsDestino(listaCorreo, post),
                subject = "[${prefijo}] ${post.asunto}",
                content = post.mensaje)
        )
    }

    private fun mailsDestino(listaCorreo: ListaCorreo, post: Post): String =
        listaCorreo.getUsuariosDestino(post)
        .joinToString(", ") { it.mailPrincipal }

}

class MalasPalabrasObserver: PostObserver{
    var malasPalabras = mutableListOf<String>("guerra", "hambre")
    var postARevisar = mutableListOf<Post>()

    override fun postRecibido(post: Post, listaCorreo: ListaCorreo) {
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

class UsuarioVerbosoObserver: PostObserver{
    val topeMails: Int = 5

    override fun postRecibido(post: Post, listaCorreo: ListaCorreo) {
        if post.emisor.usuarioEnvioMuchosMails(this){
    }



}