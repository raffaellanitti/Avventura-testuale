I-- Database delle descrizioni per "La Casa di Cenere"

INSERT INTO DESCRIZIONE(ID, COMANDO, STANZA, STATO, OGGETTO, DESCRIZIONE) VALUES
-- Stanza 1: Ingresso
(1, '0', 'Stanza1', 'true', '0', 'Una notte di pioggia, la tua auto si spegne nel nulla. Il navigatore, prima di morire, mormora: "Calcolando la tua fine...". In lontananza, una villa antica si staglia sulla collina, illuminata dai fulmini e immersa in un odore stantio che sa di muffa e mistero. Ti rifugi all''interno. Il portone si chiude alle tue spalle con un colpo secco. Il silenzio cala. La casa sembra osservarti. E non ha alcuna intenzione di lasciarti andare.'),
(2, 'Osserva', 'Stanza1', 'true', '0', 'Sei nell''ingresso della casa. L''aria è fredda e immobile, e ogni passo fa scricchiolare il pavimento sotto i tuoi piedi. Un tavolino impolverato cattura la tua attenzione.'),
(3, 'Osserva', 'Stanza1', 'true', 'Tavolo', 'Un tavolino su cui giacciono una candela e un foglio piegato, ricoperto da un velo di cenere sottile.'),
(4, 'Osserva', 'Stanza1', 'true', 'Candela', 'Una candela spenta, consumata dal tempo, ma ancora utilizzabile.'),
(5, 'Prendi', 'Stanza1', 'true', 'Candela', 'Hai preso la Candela. Emette una fiamma debole ma costante.'),
(6, 'Osserva', 'Stanza1', 'true', 'Foglio', 'Un foglio ricoperto di cenere. Leggi: "Verso nord, la luce trova ciò che l''ombra nasconde."'),
(7, 'Prendi', 'Stanza1', 'true', 'Foglio', 'Hai preso il Foglio. Le parole sembrano brillare alla luce della candela.'),
(8, 'Osserva', '0', 'true', 'Fiammiferi', 'Una scatola di fiammiferi secchi. Li hai trovati nella tua auto.'),
(9, 'Usa', 'Stanza1', 'true', 'Fiammiferi', 'Hai acceso la candela. La stanza è leggermente più illuminata.'),
(10, 'Usa', 'Stanza1', 'true', 'Candela', 'La candela illumina debolmente l''ambiente. Verso nord, noti un corridoio che prima era nell''ombra.'),

-- Stanza 2: Salone
(11, 'Osserva', 'Stanza2', 'true', '0', 'Entri in un salone ampio e silenzioso. La luce della candela tremola sulle pareti annerite dal fumo. Un grande camino domina la stanza: la cenere al suo interno è fredda, ma qualcosa brilla tra le ombre. Sul pavimento, un tappeto consunto emana un odore di muffa e copre parte delle assi scricchiolanti.'),
(12, 'Osserva', 'Stanza2', 'true', 'Camino', 'Sopra il camino, tra la polvere e la fuliggine, noti una vecchia chiave di ferro che riflette la luce della candela.'),
(13, 'Prendi', 'Stanza2', 'true', 'Chiave', 'Hai raccolto la vecchia Chiave di ferro. È pesante e fredda al tatto.'),
(14, 'Osserva', 'Stanza2', 'true', 'Chiave', 'Una vecchia chiave di ferro, arrugginita ma ancora solida.'),
(15, 'Osserva', 'Stanza2', 'true', 'Tappeto', 'Un vecchio tappeto logoro, pieno di macchie di cera e bruciature. Uno scrigno antico si trova proprio sul tappeto.'),
(16, 'Osserva', 'Stanza2', 'true', 'Scrigno', 'Uno scrigno antico, con una serratura arrugginita. Sembra poter essere aperto con la chiave trovata sul camino. All''interno troverai un amuleto d''argento a forma di goccia.'),
(17, 'Usa', 'Stanza2', 'true', 'Chiave', 'Hai inserito la Chiave arrugginita nello Scrigno e hai sentito un click. Lo scrigno è ora aperto.'),
(18, 'Prendi', 'Stanza2', 'true', 'Amuleto', 'Hai preso l''Amuleto d''argento. Senti un leggero calore emanare da esso. Sembra vibrar leggermente, come se riconoscesse la tua presenza.'),
(19, 'Osserva', 'Stanza2', 'true', 'Amuleto', 'Un amuleto d''argento a forma di goccia. È freddo al tatto e senti una vibrazione leggera: come se ti riconoscesse.'),

-- Stanza 3: Biblioteca
(20, 'Osserva', 'Stanza3', 'true', '0', 'La porta si apre su una piccola biblioteca. L''odore di carta bruciata e muffa ti avvolge. Gli scaffali sono piegati dal peso dei volumi anneriti. Solo uno sembra intatto, come se qualcuno lo avesse protetto dal tempo.'),
(21, 'Osserva', 'Stanza3', 'true', 'Scaffale', 'Tra i libri carbonizzati, un diario attira la tua attenzione: la copertina è intatta, di cuoio nero, con un simbolo inciso che ricorda quello dell''amuleto.'),
(22, 'Prendi', 'Stanza3', 'true', 'Diario', 'Hai preso il Diario. Le pagine sono intatte nonostante tutto. Sembra chiamarti.'),
(23, 'Osserva', 'Stanza3', 'true', 'Diario', 'Sfogli alcune pagine: sono piene di appunti e simboli. Parla di un rituale per riportare la casa alla pace, e cita tre oggetti sacri: luce, protezione e memoria.'),
(24, 'Usa', 'Stanza3', 'true', 'Diario', 'Leggi attentamente il Diario. Parla di una cripta segreta a nord, dove un altare attende i tre oggetti sacri. Il simbolo sulla copertina brilla debolmente.'),

-- Stanza 4: Stanza degli Specchi
(25, 'Osserva', 'Stanza4', 'true', '0', 'Appena entri, vieni avvolto da un silenzio irreale. Le pareti sono completamente ricoperte di specchi antichi, incrinati in più punti, che riflettono la luce tremolante della candela moltiplicando la tua immagine all''infinito. All''improvviso, senti il telefono vibrare nel taschino.'),
(26, 'Osserva', '0', 'true', 'Telefono', 'Il tuo cellulare. Lo schermo mostra "Segnale debole". Forse puoi usarlo per qualcosa...'),
(27, 'Usa', 'Stanza4', 'true', 'Telefono', 'Premi il tasto di sblocco. Lo schermo si accende con un messaggio inquietante: "Guarda bene. Conta ciò che ti circonda. Solo la verità aprirà la via."'),
(28, '0', 'Stanza4', 'Corretto', '0', 'Gli specchi tremano per un istante, poi si sentono scricchiolii profondi. Un passaggio segreto si apre nella parete nord. Puoi procedere.'),
(29, '0', 'Stanza4', 'Sbagliato', '0', 'Gli specchi sembrano ridere della tua risposta. Nulla accade. Forse dovresti contare con più attenzione...'),

-- Stanza 5: Cripta Finale
(30, 'Osserva', 'Stanza5', 'true', '0', 'Una fredda penombra avvolge la cripta. Davanti a te si erge un altare di pietra decorato con simboli identici a quelli trovati nelle stanze precedenti. L''aria è densa, come se la casa stessa trattenesse il respiro.'),
(31, 'Osserva', 'Stanza5', 'true', 'Altare', 'Un altare di pietra antica. Un''iscrizione è incisa profondamente: "Riporta la luce, la memoria e la protezione. Solo allora la casa avrà pace." Tre incavi sembrano attendere gli oggetti.'),
(32, 'Lascia', 'Stanza5', 'true', 'Candela', 'Posi la Candela nel primo incavo. La fiamma diventa più intensa e brillante.'),
(33, 'Lascia', 'Stanza5', 'true', 'Amuleto', 'Posi l''Amuleto nel secondo incavo. Emette un bagliore argenteo.'),
(34, 'Lascia', 'Stanza5', 'true', 'Diario', 'Posi il Diario nel terzo incavo. Le pagine iniziano a brillare con una luce dorata.');