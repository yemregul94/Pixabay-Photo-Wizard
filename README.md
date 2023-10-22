# Android Pixabay Resim Arama Uygulaması

Bu uygulama; kullanıcıların, <a href="https://pixabay.com/api/docs/"> Pixabay API</a> üzerinden resim araması yapmasına imkan sağlayan bir Android uygulamasıdır. Kullanıcılar arama çubuğuna yazdıkları etikette resimler arama yapabilir, gelen sonuçlardan ilgili resme tıkladıklarında da tekrar aynı site üzerindena alınan detaylar yüklenir. Gelen resim listesi 1 sayfadan fazla ise aşağı kaydırdıkça sonraki sayfalar da sonuçlara eklenir. Listede veya detay sayfasındaki favorilere ekle/çıkar butonlarıyla resimler favori listesine eklenip çıkartılır ve bu liste Firebase Realtime Database yardımıyla saklanır.

## Resim Arama Ekranı

Resim arama ekranında, arama kutucuğuna yazılan resim sitede arama yapılır ve sonuçlar döndürülür. Eğer bir şey yazılmazsa veya uygulama ilk açıldığında siteden en popüler sonuçlar döner. Resimlerin yanındaki favori durumu, Firebase üzerinde kullanıcıya özel olarak saklanır. Favori butonuna tıklandığında favori durumu değişir ve ilgili resmin olduğu kısım güncellenir. Uygulamadaki tüm string değerler strings dosyasından alındığı için, uygulamanın çoklu dil desteği vardır. Telefonun renk temasına göre uygulama da aydınlık veya karanlık temaya geçiş yapabilir.

Resim listesi API üzerinden sayfalar halinde gelmektedir ve her sayfada 20 resim yer almaktadır. Sonraki sayfaya geçmek için tekrar bir API sorgusu yapılması gerekmektedir ve bu sorgu, sayfanın sonuna gelindiğinde otomatik olarak yapılır. Gelen yeni sonuçlar, önceki sonuçlara eklenir ve sayfa geçişi 2 yönlü olarak sağlanır.

<a href="https://github.com/yemregul94/Pixabay-Photo-Wizard/blob/main/screenshots/dashboard.png" target="_blank">
<img src="https://github.com/yemregul94/Pixabay-Photo-Wizard/blob/main/screenshots/dashboard.png" width="200" style="max-width:100%;"></a>

<a href="https://github.com/yemregul94/Pixabay-Photo-Wizard/blob/main/screenshots/search_yazilim.png" target="_blank">
<img src="https://github.com/yemregul94/Pixabay-Photo-Wizard/blob/main/screenshots/search_yazilim.png" width="200" style="max-width:100%;"></a>

<a href="https://github.com/yemregul94/Pixabay-Photo-Wizard/blob/main/screenshots/light.png" target="_blank">
<img src="https://github.com/yemregul94/Pixabay-Photo-Wizard/blob/main/screenshots/light.png" width="200" style="max-width:100%;"></a>

<a href="https://github.com/yemregul94/Pixabay-Photo-Wizard/blob/main/screenshots/english.png" target="_blank">
<img src="https://github.com/yemregul94/Pixabay-Photo-Wizard/blob/main/screenshots/english.png" width="200" style="max-width:100%;"></a>


## Detay ve Favori Ekranı

Listeden seçilen resme ait detaylar bu detay sayfasında gösterilir. Resim detayları kullanıcının cihazının diline göre otomatik olarak değişmektedir. Detaylar ekranında da tıpkı resim ekranı gibi favorilere ekleme ve çıkartma özelliği mevcuttur.

Favoriler ekranında ise kullanıcıya ait favori listesi Firebase üzerinden alınıp buradaki resimler kullanıcıya gösterilir. Bir resmi favoriye eklemek veya çıkartmak için sağa/sola kaydırmak, yanındaki favori simgesine tıklamak veya üzerine basılı tutarak çıkan menüden seçim yapmak yeterlidir.

<a href="https://github.com/yemregul94/Pixabay-Photo-Wizard/blob/main/screenshots/details.png" target="_blank">
<img src="https://github.com/yemregul94/Pixabay-Photo-Wizard/blob/main/screenshots/details.png" width="200" style="max-width:100%;"></a>

<a href="https://github.com/yemregul94/Pixabay-Photo-Wizard/blob/main/screenshots/favorites.png" target="_blank">
<img src="https://github.com/yemregul94/Pixabay-Photo-Wizard/blob/main/screenshots/favorites.png" width="200" style="max-width:100%;"></a>

<a href="https://github.com/yemregul94/Pixabay-Photo-Wizard/blob/main/screenshots/swipe.png" target="_blank">
<img src="https://github.com/yemregul94/Pixabay-Photo-Wizard/blob/main/screenshots/swipe.png" width="200" style="max-width:100%;"></a>

<a href="https://github.com/yemregul94/Pixabay-Photo-Wizard/blob/main/screenshots/popup_menu.png" target="_blank">
<img src="https://github.com/yemregul94/Pixabay-Photo-Wizard/blob/main/screenshots/popup_menu.png" width="200" style="max-width:100%;"></a>

## Kullanıcı İşlemleri Ekranı

Kullanıcılar bu ekranlarda giriş yapma, hesap oluşturma ve şifre sıfırlama işlemleri yapabilmektedir. Yetkilendirme yöntemi olarak Firebase Auth kullanılmaktadır.

<a href="https://github.com/yemregul94/Pixabay-Photo-Wizard/blob/main/screenshots/login.png" target="_blank">
<img src="https://github.com/yemregul94/Pixabay-Photo-Wizard/blob/main/screenshots/login.png" width="200" style="max-width:100%;"></a>

<a href="https://github.com/yemregul94/Pixabay-Photo-Wizard/blob/main/screenshots/register.png" target="_blank">
<img src="https://github.com/yemregul94/Pixabay-Photo-Wizard/blob/main/screenshots/register.png" width="200" style="max-width:100%;"></a>

<a href="https://github.com/yemregul94/Pixabay-Photo-Wizard/blob/main/screenshots/reset.png" target="_blank">
<img src="https://github.com/yemregul94/Pixabay-Photo-Wizard/blob/main/screenshots/reset.png" width="200" style="max-width:100%;"></a>


## Kullanılar Teknolojiler ve Yapılar

- Kotlin & Android
- MVVM
- Retrofit
- Glide
- Firebase Auth
- Firebase Realtime Database
- Coroutines & Lifecycle
- Dagger & Hilt
- Data Binding
- View Binding
- Fragments

