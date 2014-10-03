(ns clucifer.data)

(def top-selling-books
     [{:rank 1 :title "A Tale of Two Cities" :author "Charles Dickens" :language "en" :published 1859}
      {:rank 2 :title "The Lord of the Rings" :author "J. R. R. Tolkien" :language "en" :published 1954}
      {:rank 3 :title "The Hobbit" :author "J. R. R. Tolkien" :language "en" :published 1937}
      {:rank 4 :title "Dream of the Red Chamber" :author "Cao Xueqin" :language "zh" :published 1759}
      {:rank 5 :title "On the Three Representations" :author "Jiang Zemin" :language "zh" :published 2001}
      {:rank 6 :title "And Then There Were None"  :author "Agatha Christie" :language "en" :published 1939}
      {:rank 7 :title "The Lion, the Witch and the Wardrobe" :author "C. S. Lewis" :language "en" :published 1950}
      {:rank 8 :title "She" :author "H. Rider Haggard" :language "en" :published 1887}
      {:rank 9 :title "The Little Prince" :author "Antoine de Saint-Exupéry" :language "fr" :published 1943}
      {:rank 10 :title "The Da Vinci Code" :author "Dan Brown" :language "en" :published 2003}
      {:rank 11 :title "The Catcher in the Rye" :author "J. D. Salinger" :language "en" :published 1951}
      {:rank 12 :title "The Alchemist" :author "Paulo Coelho" :language "pt" :published 1988}
      {:rank 13 :title "Steps to Christ" :author "Ellen G. White" :language "en" :published 1892}
      {:rank 14 :title "Heidi's Years of Wandering and Learning" :author "Johanna Spyri" :language "de" :published 1880}
      {:rank 15 :title "The Common Sense Book of Baby and Child Care" :author "Dr. Benjamin Spock" :language "en" :published 1946}])

(def top-grossing-films
     [{:rank 1 :title "Avatar" :gross 2787965087 :year 2009 :director "James Cameron"}
      {:rank 2 :title "Titanic" :gross  2186772302 :year 1997 :director "James Cameron"}
      {:rank 3 :title "The Avengers" :gross 1518594910 :year 2012 :director "Joss Whedon"}
      {:rank 4 :title "Harry Potter and the Deathly Hallows – Part 2" :gross 1341511219 :year 2011 :director "David Yates"}
      {:rank 5 :title "Frozen" :gross 1275772511 :year 2013 :director "Chris Buck"}
      {:rank 6 :title "Iron Man 3" :gross 1215439994 :year 2013 :director "Shane Black"}
      {:rank 7 :title "Transformers: Dark of the Moon" :gross 1123794079 :year 2011 :director "Michael Bay"}
      {:rank 8 :title "The Lord of the Rings: The Return of the King" :gross 1119929521 :year 2003 :director "Peter Jackson"}
      {:rank 9 :title "Skyfall" :gross 1108561013 :year 2012 :director "Sam Mendes"}
      {:rank 10 :title "The Dark Knight Rises" :gross 1084439099 :year 2012 :director "Christopher Nolan"}
      {:rank 11 :title "Transformers: Age of Extinction" :gross 1080870666 :year 2014 :director "Michael Bay"}
      {:rank 12 :title "Pirates of the Caribbean: Dead Man's Chest" :gross 1066179725 :year 2006 :director "Gore Verbinski"}
      {:rank 13 :title "Toy Story 3" :gross 1063171911 :year 2010 :director "Lee Unkrich"}
      {:rank 14 :title "Pirates of the Caribbean: On Stranger Tides" :gross 1045713802 :year 2011 :director "Rob Marshall"}
      {:rank 15 :title "Jurassic Park" :gross 1029153882 :year 1993 :director "Steven Spielberg"}
      {:rank 16 :title "Star Wars Episode I: The Phantom Menace" :gross 1027044677 :year 1999 :director "George Lucas"}
      {:rank 17 :title "Alice in Wonderland" :gross 1025467110 :year 2010 :director "Tim Burton"}
      {:rank 18 :title "The Hobbit: An Unexpected Journey" :gross 1017003568 :year 2012 :director "Peter Jackson"}
      {:rank 19 :title "The Dark Knight" :gross 1004558444 :year 2008 :director "Christopher Nolan"}
      {:rank 20 :title "The Lion King" :gross 987483777 :year 1994 :director "Roger Allers"}
      {:rank 21 :title "Harry Potter and the Philosopher's Stone" :gross 974755371 :year 2001 :director "Chris Columbus"}
      {:rank 22 :title "Despicable Me 2" :gross 970761885 :year 2013 :director "Pierre Coffin"}
      {:rank 23 :title "Pirates of the Caribbean: At World's End" :gross 963420425 :year 2007 :director "Gore Verbinski"}
      {:rank 24 :title "Harry Potter and the Deathly Hallows – Part 1" :gross 960283305 :year 2010 :director "David Yates"}
      {:rank 25 :title "The Hobbit: The Desolation of Smaug" :gross 958366855 :year 2013 :director "Peter Jackson"}
      {:rank 26 :title "Harry Potter and the Order of the Phoenix" :gross 939885929 :year 2007 :director "David Yates"}
      {:rank 27 :title "Finding Nemo" :gross 936743261 :year 2003 :director "Andrew Stanton"}
      {:rank 28 :title "Harry Potter and the Half-Blood Prince" :gross 934416487 :year 2009 :director "David Yates"}
      {:rank 29 :title "The Lord of the Rings: The Two Towers" :gross 926047111 :year 2002 :director "Peter Jackson"}
      {:rank 30 :title "Shrek 2" :gross 919838758 :year 2004 :director "Andrew Adamson"}
      {:rank 31 :title "Harry Potter and the Goblet of Fire" :gross 896911078 :year 2005 :director "Mike Newell"}
      {:rank 32 :title "Spider-Man 3" :gross 890871626 :year 2007 :director "Sam Raimi"}
      {:rank 33 :title "Ice Age: Dawn of the Dinosaurs" :gross 886686817 :year 2009 :director "Carlos Saldanha"}
      {:rank 34 :title "Harry Potter and the Chamber of Secrets" :gross 878979634 :year 2002 :director "Chris Columbus"}
      {:rank 35 :title "Ice Age: Continental Drift" :gross 877244782 :year 2012 :director "Steve Martino"}
      {:rank 36 :title "The Lord of the Rings: The Fellowship of the Ring" :gross 871530324 :year 2001 :director "Peter Jackson"}
      {:rank 37 :title "The Hunger Games: Catching Fire" :gross 864565663 :year 2013 :director "Francis Lawrence"}
      {:rank 38 :title "Star Wars Episode III: Revenge of the Sith" :gross 848754768 :year 2005 :director "George Lucas"}
      {:rank 39 :title "Transformers: Revenge of the Fallen" :gross 836303693 :year 2009 :director "Michael Bay"}
      {:rank 40 :title "The Twilight Saga: Breaking Dawn – Part 2" :gross 829685377 :year 2012 :director "Bill Condon"}
      {:rank 41 :title "Inception" :gross 825532764 :year 2010 :director "Christopher Nolan"}
      {:rank 42 :title "Spider-Man" :gross 821708551 :year 2002 :director "Sam Raimi"}
      {:rank 43 :title "Independence Day" :gross 817400891 :year 1996 :director "Roland Emmerich"}
      {:rank 44 :title "Shrek the Third" :gross 798958162 :year 2007 :director "Chris Miller"}
      {:rank 45 :title "Harry Potter and the Prisoner of Azkaban" :gross 796688549 :year 2004 :director "Alfonso Cuarón"}
      {:rank 46 :title "E.T. the Extra-Terrestrial" :gross 792910554 :year 1982 :director "Steven Spielberg"}
      {:rank 47 :title "Fast & Furious 6" :gross 788679850 :year 2013 :director "Justin Lin"}
      {:rank 48 :title "Indiana Jones and the Kingdom of the Crystal Skull" :gross 786636033 :year 2008 :director "Steven Spielberg"}
      {:rank 49 :title "Spider-Man 2" :gross 783766341 :year 2004 :director "Sam Raimi"}
      {:rank 50 :title "Star Wars" :gross 775398007 :year 1977 :director "George Lucas"}])
