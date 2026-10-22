package com.example.data

import com.example.model.*

object SampleDataProvider {

    val districts = listOf(
        BangladeshDistrict("dhaka", "ঢাকা (কেন্দ্রীয়)", "Dhaka (Central)", "ঢাকা", 0, 0),
        BangladeshDistrict("chittagong", "চট্টগ্রাম", "Chittagong", "চট্টগ্রাম", -5, -4),
        BangladeshDistrict("sylhet", "সিলেট", "Sylhet", "সিলেট", -6, -5),
        BangladeshDistrict("rajshahi", "রাজশাহী", "Rajshahi", "রাজশাহী", 6, 7),
        BangladeshDistrict("khulna", "খুলনা", "Khulna", "খুলনা", 3, 4),
        BangladeshDistrict("barisal", "বরিশাল", "Barisal", "বরিশাল", 1, 1),
        BangladeshDistrict("rangpur", "রংপুর", "Rangpur", "রংপুর", 4, 5),
        BangladeshDistrict("mymensingh", "ময়মনসিংহ", "Mymensingh", "ময়মনসিংহ", -1, -1),
        BangladeshDistrict("cumilla", "কুমিল্লা", "Cumilla", "চট্টগ্রাম", -3, -3),
        BangladeshDistrict("coxsbazar", "কক্সবাজার", "Cox's Bazar", "চট্টগ্রাম", -7, -6),
        BangladeshDistrict("bogura", "বগুড়া", "Bogura", "রাজশাহী", 3, 4)
    )

    val holidays = listOf(
        BangladeshHoliday("২১ ফেব্রুয়ারি", "শহীদ দিবস ও আন্তর্জাতিক মাতৃভাষা দিবস", "Language Movement Day", "জাতীয় দিবস", true),
        BangladeshHoliday("১৭ মার্চ", "জাতির পিতা বঙ্গবন্ধু শেখ মুজিবুর রহমানের জন্মবার্ষিকী", "Bangabandhu Birthday", "ঐতিহাসিক দিবস", true),
        BangladeshHoliday("২৬ মার্চ", "স্বাধীনতা ও জাতীয় দিবস", "Independence Day", "জাতীয় দিবস", true),
        BangladeshHoliday("১৪ এপ্রিল", "পহেলা বৈশাখ (বাংলা নববর্ষ)", "Bengali New Year", "সাংস্কৃতিক উৎসব", true),
        BangladeshHoliday("১ মে", "মে দিবস", "May Day", "আন্তর্জাতিক দিবস", true),
        BangladeshHoliday("১৬ ডিসেম্বর", "বিজয় দিবস", "Victory Day", "জাতীয় দিবস", true),
        BangladeshHoliday("২৫ ডিসেম্বর", "যিশু খ্রিস্টের জন্মদিন (বড়দিন)", "Christmas Day", "ধর্মীয় উৎসব", true),
        BangladeshHoliday("১ শাওয়াল", "পবিত্র ঈদুল ফিতর (চাঁদ দেখা সাপেক্ষে)", "Eid-ul-Fitr (Moon sighting)", "ধর্মীয় উৎসব", true),
        BangladeshHoliday("১০ জিলহজ", "পবিত্র ঈদুল আজহা (চাঁদ দেখা সাপেক্ষে)", "Eid-ul-Adha (Moon sighting)", "ধর্মীয় উৎসব", true)
    )

    val bcsQuestions = listOf(
        // Bangla
        Question(
            id = 1,
            subject = SubjectType.BANGLA,
            topic = "বাংলা সাহিত্য",
            questionBn = "বাংলা সাহিত্যের প্রাচীনতম নিদর্শন কোনটি?",
            questionEn = "What is the oldest specimen of Bengali literature?",
            optionsBn = listOf("শ্রীকৃষ্ণকীর্তন", "চর্যাপদ", "মনসামঙ্গল", "গীতগোবিন্দ"),
            optionsEn = listOf("Srikrishnakirtana", "Charyapada", "Manasamangal", "Gitagovinda"),
            correctIndex = 1,
            explanationBn = "চর্যাপদ হলো বাংলা ভাষার আদি বা প্রাচীন যুগের একমাত্র নির্ভরযোগ্য লিখিত নিদর্শন। এটি হরপ্রসাদ শাস্ত্রী ১৯০৭ সালে নেপালের রাজদরবারের রয়েল লাইব্রেরি থেকে আবিষ্কার করেন।",
            explanationEn = "Charyapada is the earliest work in Bengali literature, discovered by Haraprasad Shastri in 1907 from Nepal's Royal Court.",
            sourceOrYear = "৪৪তম ও ৪০তম বিসিএস"
        ),
        Question(
            id = 2,
            subject = SubjectType.BANGLA,
            topic = "বাংলা ব্যাকরণ",
            questionBn = "'সূর্য' শব্দের সমার্থক শব্দ কোনটি?",
            questionEn = "Which is a synonym of the word 'Surya' (Sun)?",
            optionsBn = listOf("অদ্রি", "অর্ক", "নীরদ", "বিধু"),
            optionsEn = listOf("Odri", "Orko", "Nirod", "Bidhu"),
            correctIndex = 1,
            explanationBn = "'অর্ক' অর্থ সূর্য। 'অদ্রি' অর্থ পর্বত, 'নীরদ' অর্থ মেঘ, এবং 'বিধু' অর্থ চাঁদ।",
            explanationEn = "'Orko' means Sun. 'Odri' means mountain, 'Nirod' means cloud, and 'Bidhu' means moon.",
            sourceOrYear = "৪৩তম বিসিএস প্রিলিমিনারি"
        ),
        // Bangladesh Affairs
        Question(
            id = 3,
            subject = SubjectType.BANGLADESH,
            topic = "মুক্তিযুদ্ধ ও সংবিধান",
            questionBn = "ঐতিহাসিক ৭ই মার্চের ভাষণ সংবিধানের কোন তফসিলে অন্তর্ভুক্ত করা হয়েছে?",
            questionEn = "In which schedule of the constitution is the historic 7th March speech included?",
            optionsBn = listOf("চতুর্থ তফসিল", "পঞ্চম তফসিল", "ষষ্ঠ তফসিল", "সপ্তম তফসিল"),
            optionsEn = listOf("Fourth Schedule", "Fifth Schedule", "Sixth Schedule", "Seventh Schedule"),
            correctIndex = 1,
            explanationBn = "বাংলাদেশের সংবিধানের পঞ্চম তফসিলে ১৯৭১ সালের ৭ই মার্চে জাতির পিতা বঙ্গবন্ধু শেখ মুজিবুর রহমানের ঐতিহাসিক ভাষণটি অন্তর্ভুক্ত রয়েছে। ষষ্ঠ তফসিলে ২৫ মার্চের স্বাধীনতার ঘোষণা রয়েছে।",
            explanationEn = "The historic 7th March speech of Bangabandhu is incorporated in the Fifth Schedule of Bangladesh Constitution.",
            sourceOrYear = "৪১তম বিসিএস প্রিলিমিনারি"
        ),
        Question(
            id = 4,
            subject = SubjectType.BANGLADESH,
            topic = "ভূগোল ও সম্পদ",
            questionBn = "বাংলাদেশের একমাত্র পাহাড়ি দ্বীপ কোনটি?",
            questionEn = "Which is the only hilly island of Bangladesh?",
            optionsBn = listOf("সেন্টমার্টিন", "মহেশখালী", "ভোলা", "কুতুবদিয়া"),
            optionsEn = listOf("St. Martin", "Moheshkhali", "Bhola", "Kutubdia"),
            correctIndex = 1,
            explanationBn = "মহেশখালী বাংলাদেশের একমাত্র পাহাড়ি দ্বীপ। এটি কক্সবাজার জেলায় অবস্থিত। আদিনাথ মন্দির এই দ্বীপের মৈনাক পর্বতের চূড়ায় অবস্থিত।",
            explanationEn = "Moheshkhali in Cox's Bazar is the only hilly island in Bangladesh, famous for the Adinath Temple on Mainak hill.",
            sourceOrYear = "৪৫তম ও ৩৮তম বিসিএস"
        ),
        // English
        Question(
            id = 5,
            subject = SubjectType.ENGLISH,
            topic = "Vocabulary & Grammar",
            questionBn = "What is the antonym of the word 'Vague'?",
            questionEn = "What is the antonym of the word 'Vague'?",
            optionsBn = listOf("Unclear (অস্পষ্ট)", "Precise (সুনির্দিষ্ট)", "Dull (নিস্তেজ)", "Faint (ম্লান)"),
            optionsEn = listOf("Unclear", "Precise", "Dull", "Faint"),
            correctIndex = 1,
            explanationBn = "'Vague' অর্থ অস্পষ্ট বা অনির্দিষ্ট। এর বিপরীত শব্দ (Antonym) হলো 'Precise' (সুনির্দিষ্ট বা স্পষ্ট)।",
            explanationEn = "'Vague' means uncertain or unclear; its exact antonym is 'Precise' or 'Clear'.",
            sourceOrYear = "৪৪তম বিসিএস"
        ),
        Question(
            id = 6,
            subject = SubjectType.ENGLISH,
            topic = "Idioms & Phrases",
            questionBn = "What does 'A bolt from the blue' mean?",
            questionEn = "What does 'A bolt from the blue' mean?",
            optionsBn = listOf("মেঘলা আকাশ", "বিনা মেঘে বজ্রপাত / অপ্রত্যাশিত বিপদ", "বিদ্যুৎ চমকানো", "শুভ সংবাদ"),
            optionsEn = listOf("Cloudy sky", "An unexpected catastrophe", "Lightning strike", "Good news"),
            correctIndex = 1,
            explanationBn = "'A bolt from the blue' একটি ইংরেজি প্রবাদ যার অর্থ হঠাৎ অপ্রত্যাশিত কোনো চরম বিপদ বা বিনামেঘে বজ্রপাত।",
            explanationEn = "'A bolt from the blue' refers to a completely unexpected piece of bad news or event.",
            sourceOrYear = "৪২তম বিসিএস"
        ),
        // Mathematics
        Question(
            id = 7,
            subject = SubjectType.MATH,
            topic = "বীজগণিত",
            questionBn = "যদি a + b = 7 এবং ab = 12 হয়, তবে a² + b² এর মান কত?",
            questionEn = "If a + b = 7 and ab = 12, what is the value of a² + b²?",
            optionsBn = listOf("২৫", "৩১", "৩৭", "৪৯"),
            optionsEn = listOf("25", "31", "37", "49"),
            correctIndex = 0,
            explanationBn = "আমরা জানি, a² + b² = (a + b)² - 2ab = (7)² - 2(12) = 49 - 24 = 25। সঠিক উত্তর ২৫।",
            explanationEn = "Using identity: a² + b² = (a + b)² - 2ab = 7² - 2(12) = 49 - 24 = 25.",
            sourceOrYear = "৪৩তম বিসিএস"
        ),
        Question(
            id = 8,
            subject = SubjectType.MATH,
            topic = "শতকরা",
            questionBn = "কোনো দ্রব্যের ক্রয়মূল্য ৮০০ টাকা, ১০% লাভে বিক্রয়মূল্য কত টাকা?",
            questionEn = "If cost price of an item is 800 Tk, what is selling price at 10% profit?",
            optionsBn = listOf("৮৫০ টাকা", "৮৮০ টাকা", "৯০০ টাকা", "৯২০ টাকা"),
            optionsEn = listOf("850 Tk", "880 Tk", "900 Tk", "920 Tk"),
            correctIndex = 1,
            explanationBn = "৮০০ টাকার ১০% = (৮০০ × ১০) / ১০০ = ৮০ টাকা। অতএব বিক্রয়মূল্য = ৮০০ + ৮০ = ৮৮০ টাকা।",
            explanationEn = "Profit = 10% of 800 = 80. Selling price = 800 + 80 = 880 Tk.",
            sourceOrYear = "৪১তম বিসিএস"
        ),
        // General Science & ICT
        Question(
            id = 9,
            subject = SubjectType.SCIENCE,
            topic = "তথ্যপ্রযুক্তি",
            questionBn = "কম্পিউটারের স্থায়ী স্মৃতিশক্তি (Non-volatile memory) কোনটি?",
            questionEn = "Which one is the permanent (non-volatile) memory of a computer?",
            optionsBn = listOf("RAM", "ROM", "Cache", "Virtual Memory"),
            optionsEn = listOf("RAM", "ROM", "Cache", "Virtual Memory"),
            correctIndex = 1,
            explanationBn = "ROM (Read Only Memory) হলো কম্পিউটারের স্থায়ী এবং নন-ভোলাটাইল মেমোরি, যা কম্পিউটার বন্ধ করলেও মুছে যায় না। RAM বিদ্যুৎ চলে গেলে মুছে যায়।",
            explanationEn = "ROM retains instructions even when power is turned off (non-volatile).",
            sourceOrYear = "৪৪তম বিসিএস"
        ),
        Question(
            id = 10,
            subject = SubjectType.INTERNATIONAL,
            topic = "আন্তর্জাতিক সংস্থা",
            questionBn = "জাতিসংঘের পরিবেশ বিষয়ক সংস্থা UNEP-এর সদর দপ্তর কোথায়?",
            questionEn = "Where is the headquarters of UNEP located?",
            optionsBn = listOf("জেনেভা", "নিউইয়র্ক", "নাইরোবি (কেনিয়া)", "প্যারিস"),
            optionsEn = listOf("Geneva", "New York", "Nairobi (Kenya)", "Paris"),
            correctIndex = 2,
            explanationBn = "UNEP (United Nations Environment Programme)-এর সদর দপ্তর কেনিয়ার রাজধানী নাইরোবিতে অবস্থিত। এটি ১৯৭২ সালে প্রতিষ্ঠিত হয়।",
            explanationEn = "UNEP headquarters is located in Nairobi, Kenya, established in 1972.",
            sourceOrYear = "৪০তম বিসিএস"
        )
    )

    val vocabWords = listOf(
        VocabWord(
            id = 1,
            word = "Perseverance",
            pronunciationBn = "পারসিভিয়ারেন্স",
            partOfSpeech = "Noun",
            meaningBn = "অধ্যবসায়, নিরবচ্ছিন্ন প্রচেষ্টা",
            definitionEn = "Persistence in doing something despite difficulty or delay in achieving success.",
            exampleSentenceEn = "His perseverance in studying helped him crack the civil service exam.",
            exampleSentenceBn = "তার পড়াশোনার অধ্যবসায় তাকে সিভিল সার্ভিস পরীক্ষায় উত্তীর্ণ হতে সাহায্য করেছিল।",
            level = VocabLevel.ADVANCED_BCS
        ),
        VocabWord(
            id = 2,
            word = "Empower",
            pronunciationBn = "এমপাওয়ার",
            partOfSpeech = "Verb",
            meaningBn = "ক্ষমতায়ন করা, সামর্থ্য বা অধিকার প্রদান করা",
            definitionEn = "To give someone the authority or power to do something.",
            exampleSentenceEn = "Education empowers girls to shape their own future.",
            exampleSentenceBn = "শিক্ষা কন্যাশিশুদের নিজেদের ভবিষ্যৎ গড়ার ক্ষমতায়ন দেয়।",
            level = VocabLevel.ESSENTIAL
        ),
        VocabWord(
            id = 3,
            word = "Meticulous",
            pronunciationBn = "মেটিকুলাস",
            partOfSpeech = "Adjective",
            meaningBn = "অতি সতর্ক, নিখুঁত ও যত্নশীল",
            definitionEn = "Showing great attention to detail; very careful and precise.",
            exampleSentenceEn = "She was meticulous about keeping her financial records accurate.",
            exampleSentenceBn = "তিনি তার আর্থিক হিসাব নির্ভুল রাখার ব্যাপারে অতি যত্নশীল ছিলেন।",
            level = VocabLevel.INTERMEDIATE
        ),
        VocabWord(
            id = 4,
            word = "Resilient",
            pronunciationBn = "রেজিলিয়েন্ট",
            partOfSpeech = "Adjective",
            meaningBn = "স্থিতিস্থাপক, প্রতিকূলতা জয় করে ঘুরে দাঁড়াতে সক্ষম",
            definitionEn = "Able to withstand or recover quickly from difficult conditions.",
            exampleSentenceEn = "The people of Bangladesh are remarkably resilient in facing floods.",
            exampleSentenceBn = "বন্যা মোকাবিলায় বাংলাদেশের মানুষ অসাধারণভাবে ঘুরে দাঁড়াতে সক্ষম।",
            level = VocabLevel.ADVANCED_BCS
        ),
        VocabWord(
            id = 5,
            word = "Commence",
            pronunciationBn = "কমেন্স",
            partOfSpeech = "Verb",
            meaningBn = "শুরু করা বা আরম্ভ হওয়া",
            definitionEn = "To begin or start.",
            exampleSentenceEn = "The national conference will commence at 10:00 AM.",
            exampleSentenceBn = "জাতীয় সম্মেলনটি সকাল ১০টায় শুরু হবে।",
            level = VocabLevel.INTERMEDIATE
        )
    )

    val conversations = listOf(
        ConversationPhrase(
            id = 1,
            category = "চাকরির ইন্টারভিউ (Job Interview)",
            englishText = "Could you please tell us a little bit about yourself?",
            banglaMeaning = "অনুগ্রহ করে আপনার নিজের সম্পর্কে সংক্ষেপে কিছু বলুন।",
            pronunciationBn = "কুড ইউ প্লিজ টেল আস আ লিটল বিট অ্যাবাউট ইয়োরসেলফ?"
        ),
        ConversationPhrase(
            id = 2,
            category = "চাকরির ইন্টারভিউ (Job Interview)",
            englishText = "I have a strong background in analytical problem solving.",
            banglaMeaning = "বিশ্লেষণধর্মী সমস্যা সমাধানে আমার ভালো দক্ষতা রয়েছে।",
            pronunciationBn = "আই হ্যাভ আ স্ট্রং ব্যাকগ্রাউন্ড ইন অ্যানালিটিক্যাল প্রবলেম সলভিং।"
        ),
        ConversationPhrase(
            id = 3,
            category = "অফিস ও কর্মক্ষেত্র (Workplace)",
            englishText = "I will send you the finalized report by the end of today.",
            banglaMeaning = "আমি আজকের মধ্যেই আপনাকে চূড়ান্ত রিপোর্টটি পাঠিয়ে দেব।",
            pronunciationBn = "আই উইল সেন্ড ইউ দ্য ফাইনালাইজড রিপোর্ট বাই দ্য এন্ড অব টুডে।"
        ),
        ConversationPhrase(
            id = 4,
            category = "দৈনন্দিন ভদ্রতা (Daily Etiquette)",
            englishText = "Thank you very much for your kind cooperation.",
            banglaMeaning = "আপনার সদয় সহযোগিতার জন্য আপনাকে অনেক ধন্যবাদ।",
            pronunciationBn = "থ্যাংক ইউ ভেরি মাচ ফর ইয়োর কাইন্ড কো-অপারেশন।"
        )
    )

    val vowelsList = listOf(
        AlphabetItem("অ", "অলি", "Bee", "অ-তে অলি", "🐝", "অলি ওড়ে ফুলে ফুলে"),
        AlphabetItem("আ", "আম", "Mango", "আ-তে আম", "🥭", "আমটি খাব পেড়ে"),
        AlphabetItem("ই", "ইলিশ", "Hilsa Fish", "ই-তে ইলিশ", "🐟", "ইলিশ ভাজা খেতে মজা"),
        AlphabetItem("ঈ", "ঈগল", "Eagle", "ঈ-তে ঈগল", "🦅", "ঈগল পাখি আকাশে ওড়ে"),
        AlphabetItem("উ", "উট", "Camel", "উ-তে উট", "🐪", "উট চলেছে মরু দেশে"),
        AlphabetItem("ঊ", "ঊষা", "Dawn", "ঊ-তে ঊষা", "🌅", "ঊষার আলো ভোরের বেলা"),
        AlphabetItem("ঋ", "ঋতু", "Season", "ঋ-তে ঋতু", "🌸", "ছয়টি ঋতুর বাংলাদেশ"),
        AlphabetItem("এ", "একতারা", "Ektara", "এ-তে একতারা", "🪕", "একতারা বাজে বাউলের সুরে"),
        AlphabetItem("ঐ", "ঐরাবত", "Elephant", "ঐ-তে ঐরাবত", "🐘", "ঐরাবত চলে হেলেদুলে"),
        AlphabetItem("ও", "ওল", "Yam", "ও-তে ওল", "🥔", "ওল খেলে ধরবে গলা"),
        AlphabetItem("ঔ", "ঔষধ", "Medicine", "ঔ-তে ঔষধ", "💊", "ঔষধ খেলে সারবে রোগ")
    )

    val consonantsList = listOf(
        AlphabetItem("ক", "কাকাতুয়া", "Cockatoo", "ক-তে কাকাতুয়া", "🦜", "কাকাতুয়ার মাথায় ঝুঁটি"),
        AlphabetItem("খ", "খরগোশ", "Rabbit", "খ-তে খরগোশ", "🐇", "খরগোশ ছানা লাফিয়ে চলে"),
        AlphabetItem("গ", "গরু", "Cow", "গ-তে গরু", "🐄", "গরু বাছুর মাঠে চরে"),
        AlphabetItem("ঘ", "ঘোড়া", "Horse", "ঘ-তে ঘোড়া", "🐎", "ঘোড়া ছুটে দ্রুত বেগে"),
        AlphabetItem("ঙ", "ব্যাঙ", "Frog", "ঙ-তে ব্যাঙ", "🐸", "ব্যাঙ ডাকে বর্ষা এলে"),
        AlphabetItem("চ", "চাঁদ", "Moon", "চ-তে চাঁদ", "🌙", "চাঁদ মামা আকাশে হাসে"),
        AlphabetItem("ছ", "ছাতা", "Umbrella", "ছ-তে ছাতা", "☂️", "ছাতা খোলো বৃষ্টির জলে"),
        AlphabetItem("জ", "জাহাজ", "Ship", "জ-তে জাহাজ", "🚢", "জাহাজ চলে সাগর জলে"),
        AlphabetItem("ঝ", "ঝরনা", "Waterfall", "ঝ-তে ঝরনা", "🌊", "ঝরনা বয়ে যায় অবিরাম"),
        AlphabetItem("ঞ", "মিঞা", "Miah", "ঞ-তে মিঞা", "👨", "মিঞা ভাই গান গায়"),
        AlphabetItem("ট", "টিয়া", "Parrot", "ট-তে টিয়া", "🦜", "টিয়া পাখির রক্তিম ঠোঁট"),
        AlphabetItem("ঠ", "ঠাকুর", "Priest", "ঠ-তে ঠাকুর", "👴", "ঠাকুরদাদার সাদা দাড়ি")
    )

    val kidsNumbers = listOf(
        AlphabetItem("১", "এক", "One", "১ - এক", "🍎", "একটি আপেল মিষ্টি লাল"),
        AlphabetItem("২", "দুই", "Two", "২ - দুই", "🦆🦆", "দুটি হাঁস জলে ভাসে"),
        AlphabetItem("৩", "তিন", "Three", "৩ - তিন", "🚗🚗🚗", "তিনটি গাড়ি রাস্তায় চলে"),
        AlphabetItem("৪", "চার", "Four", "৪ - চার", "🦋🦋🦋🦋", "চারটি প্রজাপতি রঙিন পাখা"),
        AlphabetItem("৫", "পাঁচ", "Five", "৫ - পাঁচ", "⭐", "হাতে আছে পাঁচটি আঙুল"),
        AlphabetItem("৬", "ছয়", "Six", "৬ - ছয়", "🌺", "ছয়টি ঋতু আমাদের দেশে"),
        AlphabetItem("৭", "সাত", "Seven", "৭ - সাত", "🌈", "সাতটি রঙ রংধনুতে"),
        AlphabetItem("৮", "আট", "Eight", "৮ - আট", "🐙", "আটটি পা অক্টোপাসের"),
        AlphabetItem("৯", "নয়", "Nine", "৯ - নয়", "🎈", "নয়টি বেলুন আকাশে ওড়ে"),
        AlphabetItem("১০", "দশ", "Ten", "১০ - দশ", "🔟", "দশ মিলে করি কাজ")
    )
}
