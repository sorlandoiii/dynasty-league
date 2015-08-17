(ns dynasty-league.config)

;;; TODO: Put league settings into map here.
;;; Settings

(def dynasty-settings
  {:general-settings {:teams 10
                      :roster-spots 25
                      :ir-spots 6
                      :taxi-spots 10
                      :salaries? true
                      :salary-cap 10000
                      :contracts? true
                      :num-qb 2
                      :num-rb #{1 2 3}
                      :num-wr #{1 2 3 4 5}
                      :num-te #{1 2 3 4}
                      :num-pk #{1 2}
                      :num-pn #{1 2}
                      :num-df #{1 2}}
   :qb-scoring {:pass-td 3
                :pass-yd 20
                :int -1
                :2pt 2
                :rush-td 3
                :rush-yd 10
                :fumb -1}
   :rb-scoring {:rush-yd 10
                :rush-td 3
                :reception 1
                :rec-yd 10
                :rec-td 3
                :fumb -1
                :2pt 2}
   :wr-scoring {:rush-yd 10
                :rush-td 3
                :reception 1
                :rec-yd 10
                :rec-td 3
                :fumb -1
                :2pt 2}
   :te-scoring {:rush-yd 10
                :rush-td 3
                :reception 1
                :rec-yd 10
                :rec-td 3
                :fumb -1
                :2pt 2}
   :pk-scoring {:30-39 3
                :40-49 4
                :50-plus 5
                :fg-made 3
                :xp-made 1
                :xp-missed -3}
   :pn-scoring {:yds 50
                :inside-20 2}
   :df-scoring {:int 2
                :fumb 2
                :kick-ret-td 3
                :punt-ret-td 3
                :turnover-td 3
                :blocked-punt 2}})

;;; ADPs
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
