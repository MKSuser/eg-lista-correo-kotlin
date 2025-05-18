package ar.edu.unsam.algo2.listaCorreo

data class Usuario(
    val mailPrincipal: String = "",
    val mailsAlternativos: List<String> = mutableListOf()

    var cantMailsEnviados: Int = 0

){
    fun envioMail() {
        cantMailsEnviados++
    }

    fun usuarioEnvioMuchosMails(postObserver: UsuarioVerbosoObserver){
        cantMailsEnviados > postObserver.topeMails
    }
}