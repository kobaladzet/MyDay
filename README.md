აპლიკაცია MyDay-ს საშუალებით მომხმარებელს შეუძლია გამოაქვეყნოს საუკთარი დღის ამბები, გამოიყენოს აპლიკაცია, როგორც დღიური და გაუზიაროს ამბები სხვა მომხმარებლებს ანონიმურად. ვაკვირდებით, რომ დღეს აღარავინ გვისვამს შეკითხვას "როგორი დღე გქონდა", თითქოს ამ კითხვამ ფასი დაკარგა, რადგან ხშირად ის გაუაზრებლად ისმევა. სწორედ ეს გახდა მთავარი მოტივი, რომ შეგვქმნა MyDay-ს აპლიკაცია და მიგვეცა მომხმარებლისთვის შანსი, რომ გამოეხატათ ემოციები დღის შესაბამისად სრული თავისუფლებით. აპლიკაციის დაწყებისას პირველი ფრაგმენტი არის აუთენტიფიკაციისთვის, ხოლო იმ შემთხვევაში თუ არ არსებობს მომხმარებლის პროფილი მას შეუძლია გაიაროს ავტორიზაცია, რის შედეგადაც მისი მონაცემები გადაეცემა firebase-ის console-ს და realTimeDatabase-ის დახმარებით ხდება მონაცემების შენახვა და შემდეგ მათი ამოკითხვა. email აუცილებლად უნდა იყოს რეალური რათა მომხმარებელმა შეძლოს ვერიფიკაცია. ვერიფიკაციის წარმატებით გავლის შემდეგ, მას შეუძლია სისტემაში შესვლა და პოსტების გამოქვეყნება.

![image](https://user-images.githubusercontent.com/115727018/213563196-c937ed36-248e-4e62-8c3c-941ec88a522d.png)

ის ასევე HomeFragment-ზე ნახავს სხვა მომხმარებლების გამოქვეყნებულ ანონიმურ პოსტებსაც, ხოლო ProfileFragment-ზე იქნება მხოლოდ თავისი პოსტები. პოსტის შექმნისას გვაქვს ემოციის არჩევისა და დღის ამბების გაზიარების საშუალება.

![image](https://user-images.githubusercontent.com/115710162/213546028-1a5d59ce-3862-4828-9db5-7f6795c277cc.png)

* bottom navigation და view pager 2 გავაერთიანეთ რათა მომხმარებლისთვის უფრო კომფორტული ყოფილიყო HomeFragment-სა და ProfileFragment-ს შორის ნავიგაცია. 
* RealtimeDatabase გამოყენებულია მომხმარებელთა მონაცემების შესანახად და შემდეგ ამოსაკითხად.
* sharedPreferences საშუალებით მომხმარებელს შეუძლია დარჩეს სისტემაში შესული, იმ შემთხვევაშიც თუკი აპლიკაცია გაასუფთავა მეხსიერებიდან.
* RecyclerView გვაქვს Home და Profile ფრაგმენტებზე პოსტებისთვის.
* Dialog-ის დახმარებით, როდესაც მომხმარებელი გადის სისტემიდან რწმუნდება ნამდვილად სურს თუ არა გასვლა.



