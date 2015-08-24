(ns dynasty-league.data)

;;; Vars updated during a draft

(def my-team
  {:qb 0
   :rb 0
   :wr 0
   :te 0
   :flex 0
   :k 0
   :df 0})

(def moves-made
  ["le'veon bell" "adrian peterson" "antonio brown" "jamaal charles" "eddie lacy" "matt forte" "rob gronkowski" "c.j. anderson" "demaryius thomas" "julio jones" "marshawn lynch" "dez bryant" "odell beckham jr." "lesean mccoy" "calvin johnson" "jordy nelson" "randall cobb" "demarco murray" "a.j. green" "jeremy hill" "alshon jeffery" "andrew luck" "justin forsett" "mike evans" "ty hilton" "emmanuel sanders" "frank gore" "jimmy graham" "aaron rodgers" "lamar miller" "melvin gordon" "jordan matthews" "c.j. spiller" "brandin cooks" "deandre hopkins" "mark ingram" "andre johnson" "golden tate" "travis kelce" "ben roethlisberger" "julian edelman" "andre ellington" "latavius murray" "amari cooper" "alfred morris" "keenan allen" "jarvis landry" "t.j. yeldon" "vincent jackson" "sammy watkins" "ameer abdullah" "martavis bryant" "brandon marshall" "allen robinson" "larry fitzgerald" "jonathan stewart" "tevin coleman" "jeremy maclin" "chris ivory" "russell wilson" "peyton manning" "drew brees" "todd gurley" "giovani bernard" "desean jackson" "carlos hyde" "greg olsen" "charles johnson" "martellus bennett" "tony romo" "matt ryan" "julius thomas" "jason witten" "nelson agholor" "matthew stafford" "victor cruz" "roddy white" "cam newton" "jordan cameron" "ryan tannehill" "tyler eifert" "zach ertz" "owen daniels" "shane vereen" "doug martin" "duke johnson" "rashad jennings" "mike wallace" "joique bell" "eli manning" "john brown" "legarrette blount" "isaiah crowell" "bishop sankey" "danny woodhead" "ryan mathews" "devonta freeman" "tre mason" "david cobb" "steve smith" "joseph randle" "anquan boldin" "charles sims" "reggie bush" "david johnson" "montee ball" "alfred blue" "roy helu" "seattle def" "delanie walker" "dwayne allen" "jonas gray" "josh hill" "tom brady" "eric decker" "darren mcfadden" "sam bradford" "buffalo def" "antonio gates" "kyle rudolph" "carson palmer" "philip rivers" "arian foster" "michael floyd" "houston def" "austin seferian-jenkins" "brandon lafell" "marques colston" "teddy bridgewter" "fred jackson" "joe flacco" "matt jones" "jordan reed" "terrance williams" "andy dalton" "vernon davis" "colin kaepernick" "knile davis" "coby fleener" "ladarius green" "andre williams" "eric ebron" "pierre garcon" "darren sproles" "brandon coleman" "terrance west" "davante adams" "heath miller" "eddie royal"])

(def ignored
  ["kelvin benjamin"
   "arian foster"])

;;; ADPs

(def tppr-adps
  {"Le'Veon Bell" 1 "Adrian Peterson" 2 "Antonio Brown" 3 "Jamaal Charles" 4
   "Eddie Lacy" 5 "Dez Bryant" 6 "Odell Beckham Jr." 7 "Julio Jones" 8
   "Marshawn Lynch" 9 "Demaryius Thomas" 10 "Rob Gronkowski" 11 "Matt Forte" 12
   "C.J. Anderson" 13 "Calvin Johnson" 14 "LeSean McCoy" 15 "Andrew Luck" 16
   "Jordy Nelson" 17 "DeMarco Murray" 18 "Jeremy Hill" 19 "A.J. Green" 20
   "Randall Cobb" 21 "Aaron Rodgers" 22 "Justin Forsett" 23 "Alshon Jeffery" 24
   "Mike Evans" 25 "Ty Hilton" 26 "Jimmy Graham" 27 "Frank Gore" 28
   "Lamar Miller" 29 "DeAndre Hopkins" 30 "Melvin Gordon" 31 "Brandin Cooks" 32
   "Emmanuel Sanders" 33 "Mark Ingram" 34 "Kelvin Benjamin" 35
   "Jordan Matthews" 36 "Alfred Morris" 37 "Latavius Murray" 38
   "Andre Ellington" 39 "Carlos Hyde" 40 "C.J. Spiller" 41 "Julian Edelman" 42
   "Todd Gurley" 43 "Joseph Randle" 44 "Andre Johnson" 45 "Amari Cooper" 46
   "Jonathan Stewart" 47 "Golden Tate" 48 "Keenan Allen" 49 "T.J. Yeldon" 50
   "Ameer Abdullah" 51 "Travis Kelce" 52 "Peyton Manning" 53 "Sammy Watkins" 54
   "Arian Foster" 55 "Russell Wilson" 56 "Jarvis Landry" 57 "Giovani Bernard" 58
   "Martavis Bryant" 59 "Brandon Marshall" 60 "Ben Roethlisberger" 61
   "Drew Brees" 62 "DeSean Jackson" 63 "Jeremy Maclin" 64 "Greg Olsen" 65
   "Allen Robinson" 66 "Joique Bell" 67 "Martellus Bennett" 68 "Doug Martin" 69
   "Rashad Jennings" 70 "Vincent Jackson" 71 "Shane Vereen" 72 "Tevin Coleman" 73
   "Cam Newton" 74 "Matt Ryan" 75 "LeGarrette Blount" 76 "Mike Wallace" 77
   "Nelson Agholor" 78 "Roddy White" 79 "Christopher Ivory" 80 "Isaiah Crowell" 81
   "Charles Johnson" 82 "Julius Thomas" 83 "Tre Mason" 84 "Victor Cruz" 85
   "Tony Romo" 86 "Zach Ertz" 87 "Matthew Stafford" 88 "Kevin White" 89 "Tom Brady" 90
   "Brandon LaFell" 91 "Ryan Tannehill" 92 "Devonta Freeman" 93 "Jordan Cameron" 94
   "Larry Fitzgerald" 95 "Bishop Sankey" 96 "Eli Manning" 97 "Eric Decker" 98
   "Duke Johnson" 99 "Michael Floyd" 100 "Davante Adams" 101 "John Brown" 102
   "Steve Smith" 103 "Ryan Mathews" 104 "Anquan Boldin" 105 "Jason Witten" 106
   "Torrey Smith" 107 "Pierre Garcon" 108 "Seattle Seahawks" 109 "Marques Colston" 110
   "Philip Rivers" 111 "Breshad Perriman" 112 "Alfred Blue" 113 "Danny Woodhead" 114
   "Kendall Wright" 115 "Delanie Walker" 116 "Darren McFadden" 117 "Reggie Bush" 118
   "Terrance Williams" 119 "Devante Parker" 120 "Buffalo Bills" 121 "David Johnson" 122
   "Darren Sproles" 123 "Knile Davis" 124 "Charles Sims" 125 "Houston Texans" 126
   "St. Louis Rams" 127 "Sam Bradford" 128 "Tyler Eifert" 129 "David Cobb" 130
   "Teddy Bridgewater" 131 "Colin Kaepernick" 132 "Owen Daniels" 133 "Kenny Stills" 134
   "Coby Fleener" 135 "Andre Williams" 136 "Dwayne Allen" 137 "Percy Harvin" 138
   "Michael Crabtree" 139 "Joe Flacco" 140 "Austin Seferian-Jenkins" 141
   "Antonio Gates" 142 "Steve Johnson" 143 "Roy Helu" 144 "Eddie Royal" 145
   "DeAngelo Williams" 146 "Kyle Rudolph" 147 "Josh Hill" 148 "Markus Wheaton" 149
   "New York Jets" 150 "Montee Ball" 152 "Arizona Cardinals" 153 "Brian Quick" 154
   "Devin Funchess" 155 "Carson Palmer" 156 "Jameis Winston" 157 "Dorial Green-Beckham" 158
   "Rueben Randle" 159 "Cody Latimer" 160 "Miami Dolphins" 161 "Jay Cutler" 162 "Jay Ajayi" 163
   "Eric Ebron" 164 "Jordan Reed" 165 "Vernon Davis" 166 "Marvin Jones" 167 "Andy Dalton" 168
   "Doug Baldwin" 169 "Phillip Dorsett" 170 "Ladarius Green" 171 "Jonas Gray" 172
   "Fred Jackson" 173 "Denver Broncos" 174 "Larry Donnell" 175 "Marcus Mariota" 177
   "Chris Polk" 178 "Donte Moncrief" 179 "James White" 180 "Jerick McKinnon" 181
   "Matt Jones" 182 "Derek Carr" 183 "Green Bay Packers" 184 "Dwayne Bowe" 185
   "Cameron Artis-Payne" 186 "Carolina Panthers" 187 "Jared Cook" 188
   "Ronnie Hillman" 191 "Denard Robinson" 193 "Daniel Herron" 194 "Blake Bortles" 198
   "Kenny Britt" 199 "Lorenzo Taliaferro" 200 "Charles Clay" 201 "Branden Oliver" 202
   "Philadelphia Eagles" 203 "Heath Miller" 206 "Marqise Lee" 207 "Mychal Rivera" 209
   "Tavon Austin" 210 "James Starks" 211 "Terrance West" 212 "Cecil Shorts" 213
   "Javorius Allen" 214 "Alex Smith" 216 "Baltimore Ravens" 217 "Jaelen Strong" 218
   "Theo Riddick" 219 "Maxx Williams" 220 "Robert Griffin III" 222
   "Kansas City Chiefs" 223 "Tyler Lockett" 224 "New England Patriots" 225
   "Nick Foles" 228 "Stedman Bailey" 230 "Malcom Floyd" 232 "Lance Dunbar" 233
   "Richard Rodgers" 234 "Stevan Ridley" 237 "Cincinnati Bengals" 238
   "Cole Beasley" 239 "Jace Amaro" 246 "Allen Hurns" 255 "Brian Hartline" 259
   "Khiry Robinson" 260 "Nick Toon" 261 "Brandon Coleman" 262 "Josh Robinson" 267
   "Virgil Green" 270 "Justin Hunter" 272 "Toby Gerhart" 273 "Marquess Wilson" 275
   "Greg Jennings" 276 "Robert Woods" 278 "Harry Douglas" 282 "Rob Housler" 283
   "Travaris Cadet" 284 "Clive Walford" 292 "Kamar Aiken" 294 "Benjamin Watson" 297
   "De'Anthony Thomas" 300 "Andrew Hawkins" 301 "San Francisco 49ers" 306
   "Danny Amendola" 308 "Jacob Tamme" 312 "Benny Cunningham" 315 "Jarius Wright" 326
   "Damien Williams" 334 "Bilal Powell" 335 "Taylor Gabriel" 351})

;; TODO: Make names same format as above
(def dynasty-adps
  {"DEZ BRYANT" 2.33
   "ODELL BECKHAM JR" 3.0
   "ROB GRONKOWSKI" 4.33
   "JULIO JONES" 4.83
   "LE'VEON BELL" 5.33
   "MIKE EVANS" 5.5
   "ANTONIO BROWN" 6.67
   "ANDREW LUCK" 8.33
   "AJ GREEN" 8.67
   "DEMARYIUS THOMAS" 8.67
   "ALSHON JEFFERY" 11.83
   "CALVIN JOHNSON" 13.67
   "EDDIE LACY" 14.0
   "RANDALL COBB" 14.5
   "DEANDRE HOPKINS" 15.17
   "SAMMY WATKINS" 16.5
   "AARON RODGERS" 17.33
   "JORDY NELSON" 17.5
   "JIMMY GRAHAM" 20.67
   "JAMAAL CHARLES" 21.33
   "AMARI COOPER" 21.5
   "TODD GURLEY" 22.83
   "TY HILTON" 22.83
   "BRANDIN COOKS" 23.83
   "JEREMY HILL" 24.17
   "LESEAN MCCOY" 26.0
   "KELVIN BENJAMIN" 27.33
   "KEVIN WHITE" 27.83
   "JORDAN MATTHEWS" 28.33
   "DEMARCO MURRAY" 29.83
   "MELVIN GORDON" 31.0
   "MATT FORTE" 33.5
   "KEENAN ALLEN" 33.5
   "CARLOS HYDE" 35.17
   "ALLEN ROBINSON" 37.17
   "RUSSELL WILSON" 37.17
   "CJ ANDERSON" 38.83
   "ADRIAN PETERSON" 40.0
   "DEVANTE PARKER" 40.5
   "MARSHAWN LYNCH" 41.0
   "MARTAVIS BRYANT" 43.83
   "DAVANTE ADAMS" 44.17
   "EMMANUEL SANDERS" 44.67
   "TRAVIS KELCE" 44.83
   "DORIAL GREEN-BECKHAM" 47.0
   "ARIAN FOSTER" 48.17
   "CAM NEWTON" 48.33
   "GIOVANI BERNARD" 49.17
   "GOLDEN TATE" 49.67
   "LAMAR MILLER" 50.0
   "JEREMY MACLIN" 51.5
   "MICHAEL FLOYD" 52.83
   "MARK INGRAM" 53.0
   "JARVIS LANDRY" 53.67
   "BRANDON MARSHALL" 55.0
   "TJ YELDON" 55.67
   "JULIAN EDELMAN" 55.67
   "NELSON AGHOLOR" 57.17
   "ALFRED MORRIS" 59.83
   "TEVIN COLEMAN" 62.0
   "DESEAN JACKSON" 62.17
   "DONTE MONCRIEF" 63.67
   "MATT RYAN" 63.83
   "LATAVIUS MURRAY" 65.33
   "BRESHAD PERRIMAN" 65.83
   "AMEER ABDULLAH" 67.67
   "GREG OLSEN" 69.5
   "JULIUS THOMAS" 70.5
   "ANDRE ELLINGTON" 70.83
   "CHARLES JOHNSON" 73.0
   "ISAIAH CROWELL" 73.17
   "JAELEN STRONG" 73.83
   "TORREY SMITH" 74.0
   "CODY LATIMER" 74.5
   "MATTHEW STAFFORD" 75.5
   "MIKE WALLACE" 77.33
   "DUKE JOHNSON" 77.83
   "TEDDY BRIDGEWATER" 78.5
   "JONATHAN STEWART" 78.5
   "MARTELLUS BENNETT" 79.5
   "RYAN TANNEHILL" 81.5
   "KENNY STILLS" 81.83
   "JAY AJAYI" 83.5
   "JERICK MCKINNON" 84.83
   "DEVIN FUNCHESS" 84.83
   "ANDRE JOHNSON" 87.5
   "CJ SPILLER" 88.33
   "ERIC EBRON" 88.5
   "AUSTIN SEFERIAN-JENKINS" 88.67
   "ERIC DECKER" 88.67
   "ZACH ERTZ" 90.0
   "JAMEIS WINSTON" 95.33
   "TRE MASON" 98.83
   "KENDALL WRIGHT" 99.0
   "JUSTIN FORSETT" 100.17
   "JOIQUE BELL" 100.33
   "BISHOP SANKEY" 101.67
   "SHANE VEREEN" 102.17
   "JORDAN CAMERON" 103.67
   "JOHN BROWN" 104.17
   "CHARLES SIMS" 104.17
   "BEN ROETHLISBERGER" 105.5
   "FRANK GORE" 106.33
   "PHILLIP DORSETT" 106.5
   "JOSH GORDON" 106.67
   "DAVID JOHNSON" 106.83
   "TYLER EIFERT" 106.83
   "DAVID COBB" 110.0
   "DREW BREES" 111.17
   "PERCY HARVIN" 112.17
   "MAXX WILLIAMS" 112.5
   "BRANDON LAFELL" 113.17
   "PEYTON MANNING" 113.33
   "CHRISTINE MICHAEL" 114.17
   "BRIAN QUICK" 115.67
   "RODDY WHITE" 118.0
   "VINCENT JACKSON" 118.17
   "DOUG MARTIN" 118.5
   "VICTOR CRUZ" 118.67
   "MARCUS MARIOTA" 119.67
   "DWAYNE ALLEN" 120.17
   "JOSH HILL" 120.17
   "LARRY FITZGERALD" 122.0
   "DEVONTA FREEMAN" 123.17
   "DARREN MCFADDEN" 123.67
   "MARQISE LEE" 124.0
   "DEVIN SMITH" 127.33
   "TYLER LOCKETT" 128.67
   "RUEBEN RANDLE" 129.33
   "TOM BRADY" 130.5
   "DEREK CARR" 131.17
   "KNILE DAVIS" 132.5
   "CORDARRELLE PATTERSON" 134.67
   "JUSTIN BLACKMON" 134.83
   "PIERRE GARCON" 136.17
   "SAMMIE COATES" 138.17
   "JORDAN REED" 139.33
   "LADARIUS GREEN" 139.33
   "CHRIS CONLEY" 140.5
   "KYLE RUDOLPH" 141.0
   "COLIN KAEPERNICK" 144.17
   "MICHAEL CRABTREE" 144.33
   "TERRANCE WILLIAMS" 146.0
   "RYAN MATHEWS" 146.17
   "ANDRE WILLIAMS" 147.17
   "MARVIN JONES" 147.83
   "JACE AMARO" 149.17
   "TERRANCE WEST" 151.0
   "ELI MANNING" 151.33
   "COBY FLEENER" 151.5
   "MIKE DAVIS" 152.0
   "TONY ROMO" 152.0
   "JOSEPH RANDLE" 153.17
   "JAVORIUS ALLEN" 153.5
   "PHILIP RIVERS" 153.83
   "JOE FLACCO" 156.83
   "LEGARRETTE BLOUNT" 157.33
   "JUSTIN HUNTER" 158.17
   "JOSH ROBINSON" 158.83
   "STEFON DIGGS" 162.5
   "MONTEE BALL" 163.0
   "BLAKE BORTLES" 164.17
   "JASON WITTEN" 165.33
   "RASHAD JENNINGS" 166.83
   "ANQUAN BOLDIN" 167.17
   "SAM BRADFORD" 167.5
   "JUSTIN HARDY" 168.17
   "JEREMY LANGFORD" 168.33
   "DENARD ROBINSON" 170.67
   "CHRIS IVORY" 170.67
   "ROY HELU" 173.5
   "THEO RIDDICK" 174.17
   "ANTONIO GATES" 175.0
   "JOSH HUFF" 175.0
   "TRE MCBRIDE" 175.67
   "MARQUESS WILSON" 178.17
   "STEVAN RIDLEY" 178.17
   "DELANIE WALKER" 181.17
   "MOHAMED SANU" 181.17
   "MATT JONES" 185.5
   "DEANDRE SMELTER" 187.83
   "KENNY BRITT" 189.33
   "JAY CUTLER" 189.83
   "CAMERON ARTIS-PAYNE" 190.33
   "KHIRY ROBINSON" 190.5
   "LARRY DONNELL" 190.67
   "MARQUES COLSTON" 191.17
   "DURON CARTER" 191.17
   "CLIVE WALFORD" 192.0
   "JEFF JANIS" 192.33
   "DWAYNE BOWE" 193.33
   "STEVE SMITH" 194.17
   "STEDMAN BAILEY" 194.83
   "KENNY BELL" 195.0
   "ROBERT GRIFFIN III" 196.17
   "TRENT RICHARDSON" 199.5
   "ROBERT WOODS" 199.83
   "ZACH ZENNER" 200.0
   "RASHAD GREENE" 200.17
   "KA'DEEM CAREY" 200.67
   "CHARLES CLAY" 202.33
   "NICK FOLES" 202.83
   "PAUL RICHARDSON" 203.5
   "VIRGIL GREEN" 206.0
   "MARKUS WHEATON" 206.67
   "DOUG BALDWIN" 207.33
   "REGGIE BUSH" 208.83
   "DEVANTE DAVIS" 210.5
   "VERNON DAVIS" 210.83
   "OWEN DANIELS" 211.5
   "ALFRED BLUE" 211.67
   "ANDY DALTON" 212.67
   "BRETT HUNDLEY" 213.0
   "DARREN SPROLES" 213.17
   "TAVON AUSTIN" 213.17
   "JIMMY GAROPPOLO" 213.83
   "ROBERT TURBIN" 214.17
   "DARREN WALLER" 215.83
   "TYLER GAFFNEY" 216.17
   "ALBERT WILSON" 217.0
   "BRANDEN OLIVER" 218.83
   "RICHARD RODGERS" 218.83
   "RYAN WILLIAMS" 220.0
   "CARSON PALMER" 220.17
   "CECIL SHORTS" 221.17
   "TY MONTGOMERY" 223.0
   "LORENZO TALIAFERRO" 223.0
   "DANNY WOODHEAD" 224.0
   "DAN HERRON" 226.0
   "AARON DOBSON" 226.0
   "DONTRELLE INMAN" 227.33
   "VINCE MAYLE" 227.83
   "JAMES WHITE" 228.67
   "GAVIN ESCOBAR" 229.17
   "FRED JACKSON" 230.17
   "ROD STREATER" 230.5
   "JOHNNY MANZIEL" 230.67
   "CHRIS MATTHEWS" 230.83
   "RONNIE HILLMAN" 231.0
   "BRYCE PETTY" 232.17
   "ALLEN HURNS" 232.17
   "STEVIE JOHNSON" 232.33
   "KAMAR AIKEN" 232.67
   "JONAS GRAY" 233.17
   "ALEX SMITH" 233.67
   "DEANGELO WILLIAMS" 233.83
   "NICK TOON" 234.0
   "COLE BEASLEY" 234.17
   "GENO SMITH" 235.0
   "BRANDON COLEMAN" 235.33
   "CJ FIEDOROWICZ" 235.83
   "ANDREW HAWKINS" 236.33
   "TRAVARIS CADET" 236.5
   "EDDIE ROYAL" 237.0
   "GREG JENNINGS" 237.33
   "MICHAEL CAMPANARO" 237.5
   "RYAN MALLETT" 238.67
   "JESSE JAMES" 238.83
   "HEATH MILLER" 238.83
   "MYCHAL RIVERA" 239.17
   "LANCE DUNBAR" 239.5
   "BRUCE ELLINGTON" 239.67
   "BRYCE BROWN" 239.83
   "ANTWAN GOODLEY" 239.83
   "JARED ABBREDERIS" 239.83
   "JERMAINE KEARSE" 240.0
   "JARED COOK" 240.17
   "CHRIS JOHNSON" 240.17
   "JAMES STARKS" 240.5
   "MARLON BROWN" 240.83})
