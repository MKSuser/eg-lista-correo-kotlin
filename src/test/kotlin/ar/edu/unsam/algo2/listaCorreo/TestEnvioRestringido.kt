package ar.edu.unsam.algo2.listaCorreo

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.mockk
import io.mockk.verify

class TestEnvioRestringido: DescribeSpec({
    isolationMode = IsolationMode.InstancePerTest

    describe("dada una lista de envio restringido") {
        val usuarioSuscripto = Usuario(mailPrincipal = "usuario1@usuario.com")

        val mockedMailSender = mockk<MailSender>(relaxUnitFun = true)

        val mailObserver = MailObserver()
        mailObserver.mailSender = mockedMailSender
        mailObserver.prefijo = "algo2"

        val lista = ListaCorreo().apply {
            agregarPostObserver(mailObserver)

            validacionEnvio = EnvioRestringido()

            suscribir(usuarioSuscripto)
            suscribir(Usuario(mailPrincipal = "usuario2@usuario.com"))
            suscribir(Usuario(mailPrincipal = "usuario3@usuario.com"))
        }

        it("un usuario no suscripto no puede enviar posts a la lista") {
            val usuario = Usuario(mailPrincipal = "user@usuario.com")
            val post = Post(emisor = usuario, asunto = "Pregunta", mensaje = "Hola, se que no estoy suscripto, pero quería preguntar cuándo era el asado")

            shouldThrow<BusinessException> { lista.enviarPost(post) }
        }
        it ("un usuario suscripto puede enviar post a la lista y sale a los demás usuarios de la misma") {
            val post = Post(emisor = usuarioSuscripto, asunto = "Sale asado?", mensaje = "Lo que dice el asunto")
            lista.enviarPost(post)
            verify(exactly = 1) { mockedMailSender.sendMessage(mail = Mail(from="usuario1@usuario.com", to="usuario2@usuario.com, usuario3@usuario.com", subject="[algo2] Sale asado?", content = "Lo que dice el asunto")) }
        }
    }
})