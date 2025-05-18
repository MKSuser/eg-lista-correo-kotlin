package ar.edu.unsam.algo2.listaCorreo

data class Usuario(
    val mailPrincipal: String = "",
    val mailsAlternativos: List<String> = mutableListOf(),
    var controlVerboso: Int = 0,
    var bloqueado: Boolean = false

){
    fun envioMail() {
        controlVerboso++
    }

    fun usuarioEnvioMuchosMails(postObserver: UsuarioVerbosoObserver): Boolean {
        return (controlVerboso >= postObserver.topeMails)
    }

    fun bloquear(){ bloqueado = true }

    fun desbloquear(){
        bloqueado = false
        controlVerboso = 0
    }
}