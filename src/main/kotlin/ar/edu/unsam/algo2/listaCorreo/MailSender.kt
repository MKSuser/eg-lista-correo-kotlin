package ar.edu.unsam.algo2.listaCorreo

interface MailSender {
    fun sendMail(mail: Mail)
}

data class Mail(
    val from: String,
    val to: String,
    val subject: String,
    val content: String)

interface PhoneTextSender {
    fun sendText(sms: SMS)
}

data class SMS(
    val from: String,
    val to: String,
    val subject: String,
    val content: String)
