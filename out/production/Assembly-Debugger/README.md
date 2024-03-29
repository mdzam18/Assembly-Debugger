# Assembly-Debugger
ჩვენი პროექტი წარმოადგენს Assembly Debugger-ს, მისი დახმარებით შესაძლებელია Assembly პრგრამირების ენაზე დაწერილი კოდის დებაგირება და პარალელურად მეხსიერებაში მიმდინარე პროცესების დათვალიერება.

პროექტის გასაშვებად საჭიროა vs code-ის პროექტის გახსნა, extension.ts ფაილში jar ფაილის მისამართის გაწერა და run ღილაკზე დაჭერა, რომლის შემდეგაც გაიხსნება ახალი ფანჯარა, სადაც შესაძლებელია Assembly ენაზე კოდის დაწერა, ამ ფაილის სახელი ასევე უნდა გაიწეროს Launch.json ფაილში (სწორედ აქ გაწერილი ფაილი იხსნება თავდაპირველი გაშვებისას), ამის შემდეგ კი შეგვიძლია დავსვათ breakpoint-ები და დავაჭიროთ ისევ run ღილაკს (start debugging), ფანჯრის მარცხენა ნაწილში კი დავაკვირდეთ მიმდინარე პროცესს.

![Screenshot from 2022-09-04 19-20-05](https://user-images.githubusercontent.com/57843318/189225741-bbc8bd44-9dba-4ea1-ac02-d8fa24d125bf.png)

command line-დან გაშვების ინსტრუქცია:

ჩვენი პროექტის გაშვება ასევე შესაძლებელია command line-დან, შემდეგი ბრძანების დახმარებით:

java -jar {YourJarFilePath.jar} {YourFile1Path} {YourFile2Path} ...

შემოვიღეთ assembly-ის კოდისთვის გარკვეული დამატებითი  
სტანდარტები:  
● ყველა ოპერაცია უნდა დასრულდეს “;”-ით. შესაძლოა მომხმარებელმა  
რამდენიმე ოპერაცია ერთ ხაზზე დაწეროს, ჩვენი კოდი  
ითვალისწინებს ამ შემთხვევასაც.  
● არ აქვს მნიშვნელობა space-ების განლაგებას, არაა case-sensitive.  
● აუცილებელია, რომ თითოეული ფუნქციის აღწერას თან ერთოდეს  
შემდეგი სინტაქსი: “Function ფუნქსიის სახელი;”, მაგ.: FunctionMain;  
● აუცილებელია main-ის არსებობა, რადგან თავდაპირველად გაეშვება  
სწორედ main ფუნქცია.  
● თითოეული ფუნქცია უნდა დასრულდეს RET-ით.  
