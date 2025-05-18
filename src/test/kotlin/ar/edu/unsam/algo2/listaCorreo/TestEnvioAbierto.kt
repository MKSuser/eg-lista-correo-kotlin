package ar.edu.unsam.algo2.listaCorreo

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContain
import io.mockk.mockk
import io.mockk.verify

class TestEnvioAbierto: DescribeSpec({
    isolationMode = IsolationMode.InstancePerTest

    describe("dada una lista de envio abierto") {
        val mockedMailSender = mockk<MailSender>(relaxUnitFun = true)

        val mailObserver = MailObserver()
        mailObserver.mailSender = mockedMailSender
        mailObserver.prefijo = "algo2"

        val malasPalabrasObserver = MalasPalabrasObserver()
        malasPalabrasObserver.agregarMalaPalabra("guerra")

        val lista = ListaCorreo().apply {
            agregarPostObserver(mailObserver)
            agregarPostObserver(malasPalabrasObserver)
            suscribir(Usuario(mailPrincipal = "usuario1@usuario.com"))
            suscribir(Usuario(mailPrincipal = "usuario2@usuario.com"))
            suscribir(Usuario(mailPrincipal = "usuario3@usuario.com"))
        }
        it("un usuario no suscripto puede enviar posts a la lista y le llegan solo a los suscriptos") {
            val usuario = Usuario(mailPrincipal = "user@usuario.com")
            val post = Post(emisor = usuario, asunto = "Sale asado?", mensaje = "Lo que dice el asunto guerra")
            lista.recibirPost(post)
            // esta verificación es exhaustiva pero también hace que este test se rompa muy fácilmente
            // este test es muy social, está testeando
            // 1. el mail que se genera (con todos los destinatarios en orden)
            // 2. que no se envía el mail al usuario que envía el post
            // 3. que se envía un solo mail
            //
            // otra alternativa podría ser crear tests unitarios
            verify(exactly = 1) { mockedMailSender.sendMail(mail = Mail(from="user@usuario.com", to="usuario1@usuario.com, usuario2@usuario.com, usuario3@usuario.com", subject="[algo2] Sale asado?", content = "Lo que dice el asunto guerra")) }

            malasPalabrasObserver.postARevisar shouldContain post
        }
    }
})
