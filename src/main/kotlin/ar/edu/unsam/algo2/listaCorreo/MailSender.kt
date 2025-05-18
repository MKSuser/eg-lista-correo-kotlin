package ar.edu.unsam.algo2.listaCorreo

interface MailSender {
    fun sendMessage(mail: Mail)
}

data class Mail(
    val from: String,
    val to: String,
    val subject: String,
    val content: String)


/*Pendiente*/
interface PhoneTextSender {
    fun sendMessage(sms: Sms)
}

data class Sms(
    val telefono: String,
    val texto: String,
)

interface PhoneVoiceSender {
    fun sendMessage(mensajeGrabado: MensajeGrabado)
}

data class MensajeGrabado(
    val telefono: String,
    val texto: String,
    val velocidad: Int
)
