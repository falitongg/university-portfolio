# SmartHome-omo

# Obsah
 - [Použité design patterny](#použité-design-patterny)
 - [Funkční požadavky](#funk%C4%8Dn%C3%AD-po%C5%BEadavky)
 - [Design patterny](#design-patterny)
 - [Spuštění aplikace](#spuštění-aplikace)
---
# Použité design patterny
 - [Factory Method](#factory-method-pattern)
 - [State](#state-pattern--template-method--flyweight)
 - [Flyweight](#state-pattern--template-method--flyweight)
 - Template Method ([1](#state-pattern--template-method--flyweight)) ([2](#template-method-pattern))
 - [Dependency Injection](#dependency-injection-pattern)
 - [Facade](#facade)
 - [Singleton](#singleton)
 - [Iterator](#iterator)
 
---
# Funkční požadavky
# FRQ1 – Typy spotřebičů a jejich umístění v domě

## Popis požadavku
Aplikace musí obsahovat alespoň **8 různých typů chytrých spotřebičů**.  
Každé zařízení musí být umístěno v **konkrétní místnosti**, která se nachází na určitém **patře domu** nebo ve **venkovním prostoru**.

---

## Implementace v kódu
Požadavek je realizován kombinací tříd:

- `DeviceType`
- `Device`
- `Room`
- `Floor`

---

## Typy spotřebičů (`DeviceType`)

V aplikaci je použit výčtový typ `DeviceType`, který definuje podporované typy zařízení.

**Příklady použitých typů:**

- `FRIDGE`
- `SENSOR`
- `SMART_WINDOW`
- `AIR_CONDITIONER`
- `BLINDS`
- `STOVE`
- `CIRCUIT_BREAKER`
- `CD_PLAYER`
- `WASHING_MACHINE`

Tím je splněn požadavek na minimálně **8 různých typů spotřebičů**.

---

## Reprezentace zařízení (`Device`)

Každý spotřebič je reprezentován instancí třídy `Device` (nebo jejího potomka).

**Třída `Device` obsahuje:**

- unikátní identifikátor zařízení,
- název zařízení,
- typ zařízení (`DeviceType`),
- informaci o místnosti, ve které se zařízení nachází.

**Umístění zařízení:**

- zařízení má atribut `location` typu `Room`,
- místnost je nastavena při vytvoření zařízení v konstruktoru,
- umístění zařízení je neměnné po dobu běhu simulace.

---

## Místnosti a patra (`Room`, `Floor`)

Třída `Room` reprezentuje místnost v domě.

**Každá místnost:**

- obsahuje seznam zařízení (`List<Device>`),
- je přiřazena ke konkrétnímu patru (`Floor`),
- umožňuje přidání zařízení metodou `addDevice(Device)`.

Tím vzniká hierarchická struktura:

která odpovídá reálnému uspořádání inteligentního domu.

---

## Výsledek
Aplikace obsahuje více než osm typů spotřebičů a každé zařízení je jednoznačně umístěno v konkrétní místnosti a patře domu. Funkční požadavek **FRQ1** je tímto splněn.

# FRQ2 – Interakce se zařízeními pomocí API (uživatelská i systémová)

## Popis požadavku
Interakce se zařízeními musí probíhat **prostřednictvím API**.  
Primární způsob ovládání zařízení je pomocí **členů domácnosti**, základní funkce však musí být dostupné také **systémově**, například při globálních událostech (výpadek elektřiny, bouřka).

---

## Implementace v kódu
Požadavek je realizován pomocí třídy:

- `DeviceAPI`

která slouží jako **centrální rozhraní (Facade)** pro veškeré operace se zařízeními.

---

## DeviceAPI – centrální rozhraní pro zařízení

Třída `DeviceAPI` implementuje návrhový vzor **Facade**.

### Účel Facade
- sjednocuje přístup ke všem zařízením,
- skrývá vnitřní logiku zařízení před klienty,
- zajišťuje validaci operací a konzistentní chování systému.

### Odpovědnosti `DeviceAPI`
- validace operací (stav zařízení, oprávnění uživatele),
- správa registrovaných zařízení,
- provádění hromadných systémových operací.

### Poskytované metody API
- zapnutí / vypnutí zařízení  
  - `turnOn(...)`
  - `turnOff(...)`
- použití zařízení uživatelem  
  - `use(...)`
  - `stopUsing(...)`
- oprava zařízení  
  - `repair(...)`
  - `finishRepair(...)`
  - `finishAllRepairs(...)`

Každé zařízení je před použitím zaregistrováno pomocí metody:

- `registerDevice(Device device)`

---

## Uživatelská interakce – člen domácnosti

Třída `HouseholdMember` reprezentuje člena domácnosti, který **nikdy nekomunikuje se zařízením přímo**.

### Způsob interakce
- `DeviceAPI` je do třídy `HouseholdMember` předána pomocí **Dependency Injection**,
- veškeré akce nad zařízením probíhají výhradně přes API.

### Při pokusu o interakci:
- je ověřeno, zda je osoba dostupná,
- je zkontrolováno oprávnění osoby k dané akci,
- samotná operace je provedena přes `DeviceAPI`.

### Příklady uživatelských akcí:
- zapnutí zařízení (`turnOnDevice`)
- použití zařízení (`useDevice`)
- oprava zařízení (`repairDevice`)

Tím je zajištěno oddělení logiky uživatele a zařízení a jednotný způsob ovládání.

---

## Systémová interakce – automatické reakce na události

`DeviceAPI` je využíváno také **systémovými komponentami** v reakci na události.

### Příklad: výpadek elektřiny
- třída `CircuitBreaker` reaguje na událost typu `ELECTRICITY_OUTAGE`,
- pomocí `DeviceAPI` získá seznam zařízení,
- systémově vypne všechna **nenutná aktivní zařízení**.

Tím je splněn požadavek na **automatické systémové řízení zařízení** bez zásahu uživatele.

---

## Výsledek
Veškeré interakce se zařízeními probíhají jednotně prostřednictvím `DeviceAPI`, a to jak na úrovni uživatelů, tak i na úrovni systému.  
Řešení zajišťuje konzistentní chování, centrální validaci operací a snadnou rozšiřitelnost aplikace. Funkční požadavek **FRQ2** je splněn.

# FRQ3 – Změna stavu zařízení a omezení dalších interakcí, obsah zařízení

## Popis požadavku
Interakcí se zařízením dochází ke změně jeho stavu, přičemž aktuální stav ovlivňuje, jaké další akce je možné se zařízením provádět.  
Některá zařízení navíc obsahují interní obsah (např. jídlo v lednici, CD v přehrávači), se kterým lze pracovat pouze v povolených stavech zařízení.

---

## Implementace v kódu
Tento funkční požadavek je realizován kombinací návrhových vzorů:

- **State Pattern** – řízení chování zařízení podle aktuálního stavu  
- **Flyweight Pattern** – efektivní správa instancí stavů zařízení  

Implementace je soustředěna především ve třídě `Device` a souvisejících třídách reprezentujících jednotlivé stavy zařízení.

---

## Stav zařízení

Každé zařízení se v daném okamžiku nachází **právě v jednom stavu**, který je reprezentován výčtovým typem `DeviceState`:

- `OFF`
- `ON`
- `WORKING`
- `BROKEN`
- `REPAIRING`

Aktuální stav zařízení je uložen dvojím způsobem:
- jako enum (`deviceStateEnum`) – pro snadnou kontrolu stavu,
- jako objekt typu `DeviceStateHandler` – pro řízení chování zařízení.

---

## State Pattern – řízení chování zařízení

Třída `Device` neobsahuje přímo logiku povolených operací.  
Veškeré rozhodování je **delegováno na objekt aktuálního stavu** (`currentStateHandler`).

### Kontrola povolených operací
Každá akce je před provedením ověřena metodami:
- `canBeUsed()`
- `canBeTurnedOn()`
- `canBeTurnedOff()`
- `canBeRepaired()`

Tyto metody zajišťují, že:
- zařízení nelze používat, pokud je vypnuté nebo rozbité,
- zařízení nelze zapnout, pokud je již zapnuté,
- zařízení nelze používat během opravy,
- neplatné operace jsou zakázány.

Například sporák nelze používat ve stavu `OFF`, což odpovídá reálnému chování i zadání.

---

## Implementace jednotlivých stavů

Základní chování stavů je definováno v abstraktní třídě:

- `AbstractDeviceState`

Tato třída:
- ve výchozím stavu zakazuje všechny operace,
- povoluje pouze ty akce, které jsou v daném stavu validní.

### Konkrétní stavy
Konkrétní implementace (např. `OffDeviceState`, `BrokenDeviceState`) přepisují pouze povolené operace:

- `OffDeviceState` umožňuje zapnutí zařízení,
- `BrokenDeviceState` umožňuje zahájení opravy,
- ostatní operace jsou v neplatných stavech zakázány výjimkou.

---

## Flyweight Pattern – opakované použití stavů

Instance jednotlivých stavů jsou:
- vytvořeny pouze jednou při konstrukci zařízení,
- opakovaně používány při přechodech mezi stavy.

Přechod mezi stavy probíhá změnou reference na existující `DeviceStateHandler`, což:
- šetří paměť,
- zjednodušuje správu stavů,
- zajišťuje konzistentní chování zařízení.

---

## Obsah zařízení

Zařízení pracující s interním obsahem (např. lednice, CD přehrávač) jsou implementována jako **specializovaní potomci třídy `Device`**.

Tyto třídy:
- spravují vlastní vnitřní data (např. seznam potravin, vložená média),
- umožňují práci s obsahem pouze v povolených stavech zařízení,
- respektují stavově řízené chování definované pomocí State Patternu.

---

## Výsledek
Interakce se zařízením vždy vede ke změně jeho stavu, přičemž aktuální stav jednoznačně určuje, jaké další akce jsou povoleny.  
Řešení odpovídá reálnému chování spotřebičů, omezuje neplatné operace a splňuje funkční požadavek **FRQ3** včetně podpory zařízení s interním obsahem.

# FRQ4 – Oprávnění členů domácnosti k interakci se zařízeními

## Popis požadavku
Každý člen domácnosti má předem definováno, jaká zařízení a jakým způsobem může ovládat.  
Některé role mají omezená oprávnění – například domácí mazlíček nemůže zapnout myčku a pouze vybrané osoby mohou opravovat zařízení.

---

## Implementace v kódu
Tento funkční požadavek je realizován pomocí **role-based permission systému**, který je implementován třídami:

- `Role`
- `Permission`
- `RoleFactory`

Řešení umožňuje centrálně definovat oprávnění pro jednotlivé role a konzistentně je vynucovat při každé interakci se zařízením.

---

## Oprávnění (Permissions)

Jednotlivé typy akcí, které může člen domácnosti provádět, jsou reprezentovány výčtovým typem `Permission`:

- `USE` – používání zařízení
- `TURN_ON_OFF` – zapnutí a vypnutí zařízení
- `REPAIR` – oprava zařízení
- `DO_SPORT` – používání sportovního náčiní

Tato granularita umožňuje přesně definovat, jaké operace jsou pro jednotlivé role povoleny.

---

## Role člena domácnosti

Třída `Role` reprezentuje roli člena domácnosti (např. máma, táta, dítě, miminko, domácí zvíře).

Každá role obsahuje:
- název role (`RoleName`),
- mapu povolených akcí podle typu zařízení  
  (`Map<DeviceType, Set<Permission>>`).

Třída `Role` poskytuje metodu:
- `hasPermission(DeviceType, Permission)`

Tato metoda slouží k ověření, zda může daný člen domácnosti provést konkrétní akci nad daným typem zařízení.

---

## Předdefinované role – RoleFactory

Třída `RoleFactory` slouží k vytváření předem definovaných rolí s konkrétními oprávněními:

- **Máma a táta**
  - mají plná oprávnění,
  - mohou zařízení používat, zapínat, vypínat i opravovat.

- **Děti**
  - mají omezený přístup pouze k vybraným zařízením  
    (např. lednice, CD přehrávač),
  - nemohou provádět opravy.

- **Miminko a domácí mazlíček**
  - nemají žádná oprávnění k ovládání zařízení.

Tím je zajištěno, že například domácí zvíře nebo malé dítě nemůže ovládat spotřebiče, zatímco rodiče mají plnou kontrolu nad domem.

---

## Vynucení oprávnění při interakci

Oprávnění jsou kontrolována přímo při pokusu o interakci se zařízením, například v metodách třídy `HouseholdMember`.

Při každé akci:
- je ověřeno, zda má role potřebné oprávnění,
- v případě chybějícího oprávnění není akce provedena.

Tím je zabráněno neplatným interakcím již na aplikační úrovni.

---

## Výsledek
Každý člen domácnosti má jednoznačně definována oprávnění k jednotlivým typům zařízení a akcím.  
Systém zajišťuje realistické chování domácnosti, konzistentní kontrolu oprávnění a splňuje funkční požadavek **FRQ4** v souladu se zadáním.

# FRQ5 – Aktivita členů domácnosti (používání spotřebičů a sport)

## Popis požadavku
Členové domácnosti tráví volný čas přibližně v poměru **50 % používáním spotřebičů v domě a 50 % sportovními aktivitami**.  
Pokud není k dispozici volné zařízení nebo sportovní náčiní, osoba čeká.

---

## Implementace v kódu
Tento funkční požadavek je realizován v rámci simulační logiky tříd:

- `Simulation`
- `HouseholdMember`
- `SportsEquipment`

Řešení kombinuje náhodné rozhodování o aktivitě, kontrolu dostupnosti zdrojů a stav osoby v simulaci.

---

## Rozhodování o aktivitě

V každém kroku simulace (`simulationStep`) se každý **dostupný** člen domácnosti rozhoduje o své další aktivitě na základě náhodného výběru:

- s pravděpodobností **50 %** se pokusí použít zařízení,
- s pravděpodobností **50 %** se pokusí provozovat sportovní aktivitu.

Tím je zajištěn požadovaný poměr mezi používáním spotřebičů a sportovními aktivitami v průběhu simulace.

---

## Používání sportovního náčiní

Sportovní vybavení je reprezentováno třídou `SportsEquipment`, která:

- eviduje celkový počet kusů daného náčiní,
- sleduje aktuální dostupnost,
- umožňuje více osobám současně používat různé kusy téhož vybavení.

### Průběh sportovní aktivity
Při pokusu o sportovní aktivitu:

- je vybráno dostupné sportovní náčiní,
- pokud je k dispozici:
  - osoba se přesune do místnosti, kde se náčiní nachází,
  - zahájí sportovní aktivitu,
- pokud žádné sportovní náčiní dostupné není:
  - osoba nevykonává žádnou aktivitu.

---

## Používání zařízení

Při pokusu o používání zařízení:

- je získán seznam aktuálně použitelných zařízení z `DeviceAPI`,
- osoba se pokusí zařízení zapnout a použít,
- pokud se nepodaří žádné zařízení použít (všechna jsou obsazena, vypnutá nebo rozbitá):
  - osoba zůstává neaktivní.

Používání zařízení respektuje:
- stav zařízení,
- oprávnění člena domácnosti,
- dostupnost zařízení.

---

## Chování při nedostupnosti zdrojů (čekání)

Pokud není k dispozici žádné zařízení ani sportovní náčiní, člen domácnosti:

- nevykonává žádnou aktivní činnost,
- přechází do stavu nečinnosti.

V rámci simulace je toto chování modelováno jako **pasivní pohyb mezi místnostmi** (tzv. *wander*), který reprezentuje čekání nebo nečinnost osoby.

Tento přístup:
- zachovává plynulost simulace,
- splňuje význam požadavku „čekání“,
- zajišťuje, že osoba nevyužívá žádný nedostupný zdroj.

---

## Výsledek
Simulace realisticky modeluje chování členů domácnosti, kteří se v každém kroku rozhodují mezi používáním spotřebičů a sportovními aktivitami.  
Nedostupnost zdrojů vede k nečinnosti osoby, což plně odpovídá funkčnímu požadavku **FRQ5**.

# FRQ6 – Spotřeba zařízení a její zpřístupnění pomocí API

## Popis požadavku
Každé zařízení v domě má vlastní **spotřebu zdrojů** (např. elektřiny, vody, plynu).  
Aplikace umožňuje tuto spotřebu **sledovat a získat prostřednictvím API**.

---

## Implementace v kódu
Tento funkční požadavek je realizován kombinací tříd:

- `Device`
- `ConsumptionStats`
- `DeviceAPI`

Spotřeba je vyhodnocována **průběžně v rámci simulačních kroků**.

---

## Evidence spotřeby zařízení
Každé zařízení obsahuje instanci třídy `ConsumptionStats`, která slouží ke kumulaci spotřeby jednotlivých typů zdrojů:

- **elektřina**  
- **voda**  
- **plyn**  

Třída `ConsumptionStats` poskytuje metody pro:

- přičítání spotřeby za jednotlivé simulační kroky,  
- dotazování na celkovou spotřebu konkrétního typu zdroje,  
- reset spotřeby.

---

## Definice spotřeby jednotlivých zařízení
Konkrétní spotřeba je definována v jednotlivých třídách zařízení, které dědí ze třídy `Device`.  
Každé zařízení má mapu `consumptionPerTick`, kde je určena spotřeba zařízení **za jeden simulační krok**.

Příklad:

- **Sporák** – spotřebovává plyn a malé množství elektřiny  
- **Pračka** – spotřebovává elektřinu a vodu  

Tento přístup umožňuje snadné **přidání nových zařízení** s odlišným profilem spotřeby.

---

## Aktualizace spotřeby v simulaci
V každém simulačním kroku:

- `DeviceAPI` volá metodu `updateConsumptionForAllDevices()`,  
- iteruje přes všechna registrovaná zařízení,  
- aktualizuje jejich spotřebu na základě **aktuálního stavu zařízení** (např. zapnuto, v provozu).

Spotřeba je tedy:

- akumulována pouze během běhu simulace,  
- závislá na aktuálním stavu zařízení.

---

## Přístup ke spotřebě přes API
Díky registraci všech zařízení v `DeviceAPI` je možné:

- snadno získat jejich spotřebu přes **veřejné gettery**,  
- využít data pro **generování reportů** nebo další vyhodnocování.

---

## Výsledek
Každé zařízení v aplikaci má:

- jasně definovanou spotřebu zdrojů,  
- spotřebu průběžně evidovanou během simulace,  
- dostupnou prostřednictvím API.  

Řešení umožňuje **realistické sledování provozních nákladů domácnosti**.

# FRQ7 – Umístění osob a zařízení v místnostech a generování událostí

## Popis požadavku
Každá osoba i zařízení se v každém okamžiku simulace nachází právě v jedné místnosti.  
- Osoby se mohou mezi místnostmi přesouvat.  
- Zařízení mají pevné umístění.  
- Osoby i zařízení náhodně generují **události (eventy)**, které slouží jako informace nebo upozornění (alerty).

---

## Implementace v kódu
Požadavek je realizován kombinací tříd:

- `Room`  
- `HouseholdMember`  
- `Device`  
- eventového mechanismu založeného na rozhraní `EventSource` a centrálním `EventManageru`

---

## Umístění osob a zařízení
- **Zařízení**: mají pevně danou lokaci (`location` typu `Room`), nastavenou při vytvoření a během simulace se nemění.  
- **Osoby**: mají aktuální místnost uloženou v atributu `currentRoom`.  

Přesun osoby mezi místnostmi se provádí metodou `moveTo(Room newRoom)`, která:

1. odebere osobu ze seznamu členů původní místnosti,  
2. nastaví novou místnost (`currentRoom`),  
3. přidá osobu do nové místnosti.

Tím je zajištěno, že osoba se vždy nachází **právě v jedné místnosti**.

---

## Generování událostí (EventSource)
- Osoby i zařízení implementují rozhraní `EventSource`, které definuje schopnost generovat události.  
- V každém simulačním kroku je volána metoda `eventOccured()`, kde může dojít k náhodnému vzniku události.

### Události generované osobami
- `HouseholdMember` generuje události podle role osoby:  
  - Miminko → **pláč**  
  - Domácí zvíře → **hlad**  
- Pravděpodobnost vzniku události je **náhodná**, což zvyšuje realismus simulace.

### Události generované zařízeními
- Zařízení generují události podle aktuálního stavu:  
  - Při provozu může dojít k **poruše zařízení**  
  - Někdy generují **globální události** (např. výpadek elektřiny)  
- Události mohou vznikat buď samotným zařízením, nebo uživatelem, pokud zařízení používá.

### Senzory a varovné události
- Speciální typ zařízení – **senzory**:  
  1. Měří aktuální hodnoty prostředí  
  2. Porovnávají je s definovanými mezemi  
  3. Při překročení limitů generují **varovné události** (např. únik vody, silný vítr, zvýšené CO₂)

---

## Centralizované řízení událostí
- Všechny události jsou spravovány **singletonem `EventManager`**, který:  
  - eviduje vzniklé události  
  - zajišťuje jejich následné zpracování odpovídajícími osobami nebo zařízeními

---

## Výsledek
- Každá osoba i zařízení mají **jednoznačně určenou polohu** v domě.  
- Mohou generovat **náhodné události**, které ovlivňují průběh simulace.  
- Funkční požadavek je **plně splněn**.

# FRQ8 – Odbavování událostí vhodnou osobou nebo zařízením

## Popis požadavku
Události vzniklé v průběhu simulace jsou **automaticky odbavovány** vhodnou osobou nebo zařízením.  
- Každá událost má **typ**, **zdroj** a **cíl**.  
- Událost řeší entita, která je:  
  - oprávněná,  
  - dostupná,  
  - má nejvyšší prioritu.  

Tím systém umožňuje **realistické reakce domácnosti i chytrých zařízení** na mimořádné situace (např. poruchy, varování senzorů, potřeby členů domácnosti).

---

## Implementace v kódu
Požadavek je realizován pomocí **eventově řízené architektury**:

- rozhraní `EventHandler`  
- centrální správce událostí `EventManager`  
- implementace `EventHandler` ve třídách `HouseholdMember` a `Device`  

---

## Rozhraní EventHandler
`EventHandler` definuje jednotný kontrakt pro všechny entity, které mohou události odbavovat:

- `canHandle(Event event)` – zda může entita řešit daný typ události  
- `isAvailable()` – zda je entita aktuálně dostupná  
- `getPriority(Event event)` – priorita entity pro konkrétní událost  
- `handle(Event event)` – samotné odbavení události  
- `getName()` – identifikace entity  

Díky tomu mohou události řešit **osoby i zařízení** jednotným způsobem.

---

## Centralizované rozhodování – EventManager
`EventManager` je **singleton**, který spravuje všechny události.  

Metoda `dispatchEvent(Event event, List<EventHandler> handlers)`:

1. Vyfiltruje handlery, které:  
   - mohou událost řešit (`canHandle`)  
   - jsou aktuálně dostupné (`isAvailable`)  
2. Zvolí handler s **nejvyšší prioritou** (`getPriority`)  
3. Předá mu událost k vyřízení (`handle`)  
4. Zaznamená, kdo událost odbavil  

Tím je zajištěno, že každá událost je **řešena pouze jednou** a nejvhodnější entitou.

---

## Odbavování událostí osobami
- `HouseholdMember` implementuje `EventHandler`.  
- Možnost odbavení závisí na **roli osoby**:  
  - Rodiče (máma, táta) → kritické události: porucha zařízení, únik vody, pláč miminka  
  - Děti → omezené možnosti řešení  
  - Miminko a domácí zvíře → nemohou odbavovat události  

- Priorita řešení je určena rolí (např. táta > dcera).  
- Při odbavení události osoba:  
  1. přesune se do místnosti cílové entity,  
  2. provede akci (oprava zařízení, doplnění obsahu),  
  3. přejde do stavu `HANDLING_EVENT`.

---

## Odbavování událostí zařízeními
- `Device` implementuje `EventHandler` → zařízení mohou reagovat autonomně.  
- Typické scénáře:  
  - čidlo větru → zatáhnutí žaluzií  
  - zvýšené CO₂ → otevření chytrého okna  
  - teplotní alert → zapnutí klimatizace  

- Schopnost řešit událost závisí na **DeviceType** a aktuálním **stavu zařízení**.

---

## Příklady realizovaných scénářů
- Čidlo na vítr → žaluzie se automaticky zatáhnou  
- Výpadek elektřiny → jistič reaguje systémově  
- Únik vody → rodiče řeší podle role  
- Pláč miminka → řeší máma nebo táta  
- Porucha zařízení → oprava oprávněnou osobou  
- Prázdná lednice / konec CD → doplnění obsahu osobou  

---

## Výsledek
- Události jsou **automaticky, deterministicky a realisticky** odbavovány.  
- Architektura umožňuje **snadné rozšíření** o nové typy událostí a handlery.  
- Funkční požadavek je **plně splněn**.

# FRQ9 – Generování reportů po ukončení simulace

## Popis požadavku
Aplikace je schopna po ukončení běhu simulace vygenerovat přehledné reporty, které shrnují:

- konfiguraci domu a jeho strukturu  
- vzniklé a odbavené události  
- aktivitu jednotlivých členů domácnosti  
- spotřebu energií a zdrojů jednotlivými zařízeními  

Reporty slouží jako **výstup simulace** a umožňují zpětnou analýzu chování domácnosti i chytrých zařízení.

---

## Řešení
Požadavek je realizován pomocí **samostatného reportovacího modulu**:

- založeného na abstraktní třídě `Report`  
- konkrétních implementacích pro jednotlivé typy reportů  

Reporty jsou **generovány po dokončení simulace** a ukládány do textových souborů.

---

## Společný základ reportů
Všechny reporty dědí od abstraktní třídy `Report`, která definuje:

- `generateReport()` – abstraktní metoda pro vytvoření textového obsahu reportu  
- `writeToFile(String fileName)` – metoda, která:
  - vytvoří adresář `reports`,  
  - zapíše vygenerovaný obsah do textového souboru  

Tím je zajištěna **jednotná struktura reportů** a **snadná rozšiřitelnost** o další typy.

---

## HouseConfigurationReport
- Obsahuje kompletní hierarchickou strukturu domu:  
  - dům → patra → místnosti → zařízení  
- Přehled všech členů domácnosti včetně rolí a oprávnění  
- Vytváří se z **objektového modelu domu** a slouží k ověření počáteční konfigurace simulace

---

## EventReport
- Založen na datech uložených v **singletonu `EventManager`**, který eviduje všechny události simulace  
- Události v reportu:  
  - seskupeny podle typu události  
  - obsahují zdroj události  
  - obsahují cílovou entitu  
  - zaznamenávají, kdo událost odbavil  
- Umožňuje **detailní analýzu chování systému** v nestandardních situacích

---

## ActivityAndUsageReport
- Generuje report **aktivity členů domácnosti**  
- Prochází všechny objekty typu `HouseholdMember` a vypisuje:  
  - kolikrát daná osoba použila konkrétní zařízení  
  - kolikrát použila sportovní náčiní  
- Data jsou získávána z interních statistik osob (`deviceUsageCount`, `sportsEquipmentUsageCount`)  
- Report reflektuje **reálné chování osob** v domě

---

## ConsumptionReport
- Poskytuje přehled **spotřeby zdrojů** v domácnosti  
- Zobrazuje spotřebu jednotlivých zařízení (elektřina, voda, plyn)  
- Vypočítává **celkovou spotřebu domácnosti** za dobu simulace  
- Data jsou získávána z objektů `ConsumptionStats`, které každé zařízení aktualizuje v průběhu simulace

---

## Spuštění generování reportů
- Reporty se vytvářejí po dokončení simulace v hlavní třídě `Main`  
- Po zavolání `simulation.run(...)` jsou vytvořeny instance jednotlivých reportů  
- Obsah je uložen do textových souborů pomocí metody `writeToFile(...)`

---

## Výsledek
- Aplikace **automaticky generuje ucelenou sadu reportů** po ukončení simulace  
- Poskytuje detailní přehled o:  
  - konfiguraci domu  
  - vzniklých událostech  
  - aktivitě osob  
  - spotřebě zdrojů  
- Architektura reportovací části je **modulární, přehledná a snadno rozšiřitelná**  
- Funkční požadavek je **plně splněn**

# FRQ10 – Předpřipravená názorná konfigurace domu

## Popis požadavku
Aplikace obsahuje předpřipravenou názornou konfiguraci inteligentního domu, která splňuje minimální požadavky na:

- počet členů domácnosti  
- počet zařízení  
- sportovní náčiní  
- počet místností  

Konfigurace slouží jako **výchozí scénář simulace** a demonstruje implementované funkční požadavky systému.

---

## Řešení
Požadavek je realizován pomocí **externího konfiguračního souboru ve formátu JSON**, který popisuje:

- kompletní strukturu domu  
- rozmístění zařízení  
- členy domácnosti  
- sportovní vybavení  

Konfigurace je načtena při startu aplikace a převedena do **objektového modelu simulace**.

---

## Struktura domu
Konfigurační soubor definuje dům jako hierarchii:

- patra (`floors`)  
- místnosti (`rooms`)  
- zařízení (`devices`)  

Vzorová konfigurace obsahuje:

- 2 patra (přízemí a první patro)  
- 6+ místností, včetně venkovního prostoru (zahrada)  
- místnosti různých typů: kuchyně, obývací pokoj, ložnice, koupelna, zahrada  

Každé zařízení je **jednoznačně přiřazeno ke konkrétní místnosti**, a tím i k patru domu.

---

## Zařízení
Konfigurace obsahuje:

- více než **20 kusů zařízení**  
- alespoň **8 různých typů zařízení**, například:
  - lednice  
  - sporák  
  - CD přehrávač  
  - žaluzie  
  - chytré okno  
  - pračka  
  - klimatizace  
  - senzory (CO₂, vlhkost, teplota, vítr)  
  - jistič  

Senzory mají v konfiguraci **specifikován typ měření (`sensorType`)**, který určuje jejich chování a generované události během simulace.

---

## Sportovní náčiní
Součástí konfigurace je také **sportovní vybavení**:

- 5 kusů sportovního náčiní  
- rozděleno do typů (kolo, lyže)  
- umístěno ve venkovním prostoru (zahrada)  

Sportovní náčiní je **sdíleno mezi členy domácnosti** a je používáno v rámci simulace volného času.

---

## Členové domácnosti a oprávnění
Konfigurace definuje **6 členů domácnosti**, z nichž každý má přiřazenou roli:

- máma  
- táta  
- dcera  
- syn  
- miminko  
- domácí zvíře  

Role určují:

- oprávnění k používání zařízení  
- možnost odbavovat události  
- chování v simulaci (např. miminko generuje pláč, zvíře hlad)  

Tím je zajištěno, že **každý člen domácnosti má odlišné schopnosti a omezení**.

---

## Použití konfigurace v simulaci
- Konfigurační soubor je použit při startu aplikace jako **vstup pro vytvoření instance simulace**  
- Veškeré objekty domu, zařízení, osob a sportovního vybavení jsou vytvořeny **automaticky na základě této konfigurace**  
- Není potřeba měnit zdrojový kód

---

## Výsledek
Aplikace obsahuje **předpřipravenou názornou konfiguraci inteligentního domu**, která splňuje všechny minimální požadavky:

- osoby  
- zařízení  
- místnosti  
- sportovní vybavení  

Externí konfigurační soubor umožňuje **snadnou úpravu scénáře simulace** a přehlednou demonstraci všech implementovaných funkčních požadavků.  

**FRQ10 je plně splněn.**

---
# Design patterny



## Factory Method Pattern
**Účel**: <br>
Factory Method Pattern řeší problém vytváření objektů tím, že skrývá konstruktory konkrétních tříd a poskytuje jednotné rozhraní pro jejich vytváření. Pattern centralizuje logiku vytváření objektů na jednom místě.

**Implementace v kódu**:

```java
/**
 * Factory Method Pattern for creating devices.
 * Centralizes device creation logic with auto-incremented IDs.
 */
public class DeviceFactory {

    private static int devicerIdCounter = 0;

    /**
     * Creates device based on DeviceType.
     * Assigns auto-incremented ID and initial OFF state.
     * @param type Device type
     * @param location Room where device is located
     * @param name Device name
     * @return Device instance
     * @throws IllegalArgumentException if device type is unknown
     */
    public static Device createDevice(DeviceType type, Room location, String name, DeviceAPI deviceAPI) {
        int id = devicerIdCounter++;
        DeviceState initialState = DeviceState.OFF;

        return switch (type) {
            case FRIDGE -> new Fridge(id, location, name, initialState);
            case SMART_WINDOW -> new SmartWindow(id, location, name, initialState);
            case AIR_CONDITIONER -> new AirConditioner(id, location, name, initialState);
            case BLINDS -> new Blinds(id, location, name, initialState);
            case STOVE -> new Stove(id, location, name, initialState);
            case CIRCUIT_BREAKER -> new CircuitBreaker(id, location, name, initialState, deviceAPI);
            case CD_PLAYER -> new CDplayer(id, location, name, initialState);
            case WASHING_MACHINE -> new WashingMachine(id, location, name, initialState);
            default -> throw new IllegalArgumentException("Unknown device type: " + type);
        };
    }

    /**
     * Creates sensor based on SensorType.
     * Assigns auto-incremented ID and initial ON state.
     * @param sensorType Type of sensor
     * @param location Room where sensor is located
     * @param name Sensor name
     * @return AbstractSensor instance
     */
    public static AbstractSensor createSensor(SensorType sensorType, Room location, String name) {
        int id = devicerIdCounter++;
        DeviceState initialState = DeviceState.ON; // Sensors start ON

        return switch (sensorType){
            case HUMIDITY -> new HumiditySensor(id, location, name, initialState);
            case CO2 -> new СO2Sensor(id, location, name, initialState);
            case WIND -> new WindSensor(id, location, name, initialState);
            case TEMPERATURE -> new TemperatureSensor(id, location, name, initialState);
        };
    }
}
```

**Příklad použití:**

```java
//Device
        Device fridge = DeviceFactory.createDevice(DeviceType.FRIDGE, kitchen, "Fridge", deviceAPI);
        kitchen.addDevice(fridge);
        deviceAPI.registerDevice(fridge);
//Sensor
        Device windSensor = DeviceFactory.createSensor(SensorType.WIND, garden, "Wind sensor");
        garden.addDevice(windSensor);
        deviceAPI.registerDevice(windSensor);
```

**Důvod:**

Pattern byl zvolen v projektu z několika důvodů:
​

 - **Centralizace logiky vytváření:** Všechna zařízení se vytvářejí na jednom místě, což usnadňuje správu a údržbu

 - **Automatická správa ID:** Factory automaticky přiděluje unikátní ID pomocí devicerIdCounter, což eliminuje možnost duplicitních ID

 - **Konzistentní inicializace:** Všechna zařízení začínají ve stavu OFF, sensory ve stavu ON, což zajišťuje konzistentní chování systému

 - **Rozšiřitelnost:** Přidání nového typu zařízení vyžaduje pouze úpravu switch příkazu ve factory, nikoliv změny v mnoha částech kódu

---

## State Pattern + Template Method + Flyweight
> Poznámka: Implementace **State Pattern** v tomto projektu 
současně využívá <br> **Template Method Pattern** pro 
unifikované přechody mezi stavy a **Flyweight Pattern** pro úsporu paměti.

**Účel**:

- **State Pattern** řeší problém objektu, který se chová různě v závislosti na svém vnitřním stavu. Místo používání mnoha if-else nebo switch podmínek rozesetých po celém kódu, pattern zapouzdřuje chování specifické pro každý stav do samostatných tříd.
​

- **Template Method Pattern** v rámci State definuje společnou "šablonu" pro přechody mezi stavy, kterou všechny konkrétní stavy používají. Zajišťuje konzistentní způsob změny stavu napříč celým systémem.
​

- **Flyweight Pattern** zajišťuje, že state objekty jsou vytvořeny pouze jednou při inicializaci zařízení a následně jsou znovu používány. To šetří paměť, protože state objekty neobsahují měnící se data (pouze logiku).

**Implementace v kódu**:

Context třída `Device`, jen ukázka:

```java
public class Device implements EventSource, EventHandler {
    // State Pattern - jednotlivé stavy
    private final DeviceStateHandler offState;
    private final DeviceStateHandler onState;
    private final DeviceStateHandler workingState;
    private final DeviceStateHandler brokenState;
    private final DeviceStateHandler repairingState;
    private DeviceStateHandler currentStateHandler;
    private DeviceState deviceStateEnum;

    protected Device(int id, Room location, String name, 
                    DeviceState deviceStateEnum, DeviceType type) {
        // Inicializace všech state handlerů
        this.offState = new OffDeviceState(this);
        this.onState = new OnDeviceState(this);
        this.workingState = new WorkingDeviceState(this);
        this.brokenState = new BrokenDeviceState(this);
        this.repairingState = new RepairingDeviceState(this);
        this.currentStateHandler = offState;
    }

    // Delegování na aktuální stav
    void performTurnOn() {
        currentStateHandler.onTurnOn();
    }

    void performTurnOff() {
        currentStateHandler.onTurnOff();
    }

    void performUse(HouseholdMember member) {
        currentStateHandler.onUse(member);
    }
}

```

```java
//DeviceStateHandler interface
public interface DeviceStateHandler {
    // Kontroly možností
    boolean canBeUsed();
    boolean canBeTurnedOn();
    boolean canBeTurnedOff();
    boolean canBeRepaired();

    // Akce vyvolávající přechody mezi stavy
    void onUse(HouseholdMember member);
    void onStopUsing(HouseholdMember member);
    void onTurnOn();
    void onTurnOff();
    void onBreak();
    void onStartRepair();
    void onFinishRepair();
}
```

```java
/**
 * Abstract base class for device states using State Pattern.
 * Implements Flyweight Pattern for state reuse.
 * Provides default implementations that throw exceptions - subclasses override allowed operations.
 */
public abstract class AbstractDeviceState implements DeviceStateHandler {

    protected final Device device;

    /**
     * Creates state handler for device.
     * @param device Device to handle state for
     */
    protected AbstractDeviceState(Device device) {
        this.device = device;
    }

    /**
     * Changes device to target state using Flyweight pattern.
     * Reuses existing state handlers instead of creating new ones.
     * @param targetState State to transition to
     */
    protected void changeToState(DeviceState targetState) {
        device.setCurrentStateHandler(getStateHandler(targetState));
        device.setDeviceStateEnum(targetState);
    }

    /**
     * Gets reusable state handler for target state (Flyweight pattern).
     * @param targetState Target state
     * @return State handler instance
     */
    private DeviceStateHandler getStateHandler(DeviceState targetState) {
        return switch (targetState) {
            case OFF -> device.getOffState();
            case ON -> device.getOnState();
            case WORKING -> device.getWorkingState();
            case BROKEN -> device.getBrokenState();
            case REPAIRING -> device.getRepairingState();
        };
    }

    @Override
    public boolean canBeTurnedOff() {
        return false;
    }

    @Override
    public boolean canBeUsed() {
        return false;
    }

    @Override
    public boolean canBeTurnedOn() {
        return false;
    }

    @Override
    public boolean canBeRepaired() {
        return false;
    }

    @Override
    public void onUse(HouseholdMember member) {
        throw new IllegalStateException("Cannot be called in current state");
    }

    @Override
    public void onStopUsing(HouseholdMember member) {
        throw new IllegalStateException("Cannot be called in current state");
    }

    @Override
    public void onTurnOn() {
        throw new IllegalStateException("Cannot be called in current state");
    }

    @Override
    public void onTurnOff() {
        throw new IllegalStateException("Cannot be called in current state");
    }

    /**
     * Breaks device - allowed from any state.
     */
    @Override
    public void onBreak() {
        changeToState(DeviceState.BROKEN);
    }

    @Override
    public void onStartRepair() {
        throw new IllegalStateException("Cannot be called in current state");
    }

    @Override
    public void onFinishRepair() {
        throw new IllegalStateException("Cannot be called in current state");
    }
}
```

Příklad konkrétní implementace (`OffDeviceState`):
```java
/**
 * OFF state - device is powered off.
 * Can only be turned on.
 */
public class OffDeviceState extends AbstractDeviceState {

    public OffDeviceState(Device device) {
        super(device);
    }

    @Override
    public boolean canBeTurnedOn() {
        return true;
    }

    @Override
    public void onTurnOn() {
        changeToState(DeviceState.ON);
        device.setDeviceStateEnum(DeviceState.ON);
    }
}
```

**Důvod:**

**State Pattern** byl zvolen z těchto důvodů:
​

- **Eliminace složitých podmínek:** Bez State Pattern by kód obsahoval mnoho if-else pro kontrolu aktuálního stavu před každou operací (např. "můžu zapnout jen když jsem OFF")
​

- **Bezpečnost přechodů:** Každý stav definuje pouze operace, které jsou z něj povolené - pokus o neplatnou operaci vyhodí exception
​

- **Simulace reálného chování:** Smart home zařízení mají v realitě komplexní životní cyklus (zapnuto → používá se → poškozeno → opravuje se) který pattern přesně modeluje
​

**Template Method Pattern** byl přidán pro:
​

- **Konzistenci přechodů:** Všechny state třídy používají stejnou metodu changeToState(), což zajišťuje, že přechod mezi stavy probíhá vždy stejně (nastavení handleru i enumu)
​

- **Eliminaci duplicitního kódu:** Bez template method by každá state třída musela implementovat logiku přechodu samostatně, což by vedlo k opakování kódu
​

- **Snadnou údržbu:** Pokud se změní způsob přechodu mezi stavy, stačí upravit pouze metodu changeToState() v AbstractDeviceState
​

**Flyweight Pattern** byl implementován kvůli:
​

- **Úspoře paměti:** V simulaci může být desítky až stovky zařízení - bez Flyweight by každé zařízení vytvářelo 5 nových state objektů při každém přechodu
​

- **Výkonu:** State objekty jsou immutable (obsahují pouze logiku, ne data), takže jejich znovupoužití je bezpečné a rychlé
​

- **Jednoduchosti:**  Každý Device má svých vlastních 5 state handlerů. State objekty se vytvoří jednou v konstruktoru Device a pak se jen mění reference `currentStateHandler`, místo vytváření nových instancí.


## Template Method Pattern

**Účel**: <br>
Template Method Pattern definuje kostru algoritmu v abstraktní 
třídě a nechává podtřídy implementovat 
specifické kroky.

**Implementace v kódu**:

Abstraktní třída (`AbstractSensor`), definuje šablonu:

```java
/**
 * Abstract base class for all sensors.
 * Uses Template Method Pattern for measurement logic.
 * Subclasses implement performMeasurement() to provide specific sensor logic.
 */
public abstract class AbstractSensor extends Device {

    private final SensorType sensorType;

    /**
     * Current measured value.
     */
    protected double currentValue;

    /**
     * Threshold values for generating events.
     */
    protected double minThreshold;
    protected double maxThreshold;

    /**
     * Creates sensor with specified type.
     * All sensors consume minimal electricity (0.001 per tick).
     * @param id Sensor ID
     * @param location Room where sensor is installed
     * @param name Sensor name
     * @param deviceStateEnum Initial state
     * @param sensorType Type of sensor
     */
    public AbstractSensor(int id, Room location, String name,
                          DeviceState deviceStateEnum, SensorType sensorType) {
        super(id, location, name, deviceStateEnum, DeviceType.SENSOR);
        this.sensorType = sensorType;
        this.currentValue = 0.0;
        consumptionPerTick.put(ConsumptionType.ELECTRICITY, 0.001);
    }

    /**
     * Template Method: performs measurement if sensor is ON.
     * Calls abstract performMeasurement() implemented by subclasses.
     */
    public final void measure(){
        if (getDeviceStateEnum() != DeviceState.ON){
            return;
        }

        currentValue = performMeasurement();
    }

    /**
     * Abstract method for specific sensor measurement logic.
     * Implemented by subclasses (HumiditySensor, TemperatureSensor, etc.).
     * @return Measured value
     */
    protected abstract double performMeasurement();

    /**
     * Checks measured value and generates events if critical.
     */
    protected void checkThresholds(){
        if (currentValue < minThreshold || currentValue > maxThreshold) {
            EventType type = switch (sensorType) {
                case TEMPERATURE -> EventType.TEMPERATURE_ALERT;
                case HUMIDITY -> EventType.WATER_LEAK;
                case WIND -> EventType.WIND_ALERT;
                case CO2 -> EventType.CO2_ALERT;
            };

            EventManager.getInstance().generateEvent(this, this, type);
        }
    }
    @Override
    public void eventOccuired() {
        measure();
        checkThresholds();
    }
```

Příklad konkrétní implementace (`HumiditySensor`):

```java
/**
 * Humidity sensor - measures relative humidity (%).
 * Critical values: < 30% (too dry), > 80% (mold risk/leak).
 */
public class HumiditySensor extends AbstractSensor {

    private static final Random random = new Random();

    public HumiditySensor(int id, Room location, String name, DeviceState deviceStateEnum) {
        super(id, location, name, deviceStateEnum, SensorType.HUMIDITY);

        setMinThreshold(30.0);
        setMaxThreshold(80.0);
    }

    /**
     * Generates realistic humidity value using Gaussian (normal) distribution.
     * Mean: 45% (typical indoor humidity)
     * Standard deviation: 10%
     * ~68% of values fall within 35-55%
     * Extreme values trigger events only occasionally
     */
    @Override
    protected double performMeasurement() {
        double baseHumidity = 45.0 + random.nextGaussian() * 10.0;

        // Clamp to valid range [0-100%]:
        // - If < 0 → set to 0
        // - If > 100 → set to 100
        // - Otherwise → keep original value
        return Math.max(0.0, Math.min(100.0, baseHumidity));
    }
}

```

**Důvod:**

Pattern byl zvolen v projektu z těchto důvodů:
​

- **Odstranění duplicitního kódu:** Všechny sensory sdílejí společnou logiku (kontrola stavu, kontrola thresholdů, generování eventů), ale liší se ve způsobu měření
​​

 - **Snadné přidání nového sensoru:** Stačí vytvořit novou třídu, dědit z AbstractSensor a implementovat jen performMeasurement() - zbytek je zdědený
​

- **Rozdělení zodpovědnosti:** Abstraktní třída řeší "kdy a jak" měřit, konkrétní třídy řeší "co" měřit
​

- **Realistická simulace:** Každý typ sensoru má vlastní rozdělení hodnot odpovídající reálným fyzikálním podmínkám



## Dependency Injection Pattern


**Účel**: <br>
Dependency Injection odděluje objekt od vytváření jeho závislostí, takže místo aby si třída sama vytvářela, co potřebuje, dostane to zvenku jako parametr. 

**Implementace v kódu**:

```java
/**
 * Represents a person living in the house.
 * Can use devices and sports equipment based on role permissions.
 * Implements EventHandler for reacting to events and EventSource for generating events.
 */
public class HouseholdMember implements EventHandler, EventSource {

    private final String name;
    private final Role role;
    private Room currentRoom;
    private ActivityState state;
    private final DeviceAPI deviceAPI;
    private final Map<Device, Integer> deviceUsageCount;
    private final Map<SportsEquipment, Integer> sportsEquipmentUsageCount;

    /**
     * Creates a new household member.
     * Uses Dependency Injection for DeviceAPI.
     * @param name Person's name
     * @param role Person's role (defines permissions)
     * @param initialRoom Starting room
     * @param deviceAPI API for device interactions
     */
    public HouseholdMember(String name, Role role, Room initialRoom, DeviceAPI deviceAPI){
        this.name = name;
        this.role = role;
        this.currentRoom = initialRoom;
        this.state = ActivityState.IDLE;
        this.deviceUsageCount = new HashMap<>();
        this.sportsEquipmentUsageCount = new HashMap<>();
        this.deviceAPI = deviceAPI;
        initialRoom.addHouseholdMember(this);
    }

    /**
     * Uses device if person is available and has permission.
     * Records usage for statistics.
     * @param device Device to use
     * @return true if successfully started using device
     */
    public boolean useDevice(Device device) {
        if (!isAvailable()){
            return false;
        }

        if (!canPerform(device, Permission.USE)){
            return false;
        }

        boolean success = deviceAPI.use(device, this);
        if (success){
            moveTo(device.getLocation());
            this.state = ActivityState.USING_DEVICE;
            recordDeviceUsage(device);
            stopUsingDevice(device);
        }

        return success;
    }
    
    //...
```

**Důvod:**
 - HouseholdMember neobsahuje žádný kód typu new DeviceAPIImpl(), takže není pevně svázaný s konkrétní implementací API.
​
## Facade


**Účel**: <br>
Facade Pattern poskytuje jednoduché, sjednocené rozhraní k složitému subsystému. Místo aby klient musel znát všechny detaily a volat mnoho metod různých tříd, Facade nabízí vyšší úroveň abstrakce s jednoduchými metodami, které skrývají komplexitu za sebou.

**Implementace v kódu**:
```java
/**
 * Facade for device operations.
 * Provides simplified interface for device interactions with validation.
 * Manages device registry and batch operations.
 */
public class DeviceAPI {

    private final List<Device> devices;

    /**
     * Creates new DeviceAPI instance.
     */
    public DeviceAPI() {
        this.devices = new ArrayList<>();
    }

    /**
     * Registers device in the system.
     * @param device Device to register
     */
    public void registerDevice(Device device) {
        devices.add(device);
    }

    /**
     * Gets all registered devices.
     * @return Unmodifiable list of devices
     */
    public List<Device> getDevices() {
        return Collections.unmodifiableList(devices);
    }


    /**
     * Starts using device with validation.
     * Validates that device and member are not null and device can be used.
     * @param device Device to use
     * @param member Person using the device
     * @return true if successfully started using
     */
    public boolean use(Device device, HouseholdMember member) {
        if (device == null || member == null) return false;
        if (!device.canBeUsed()) return false;
        device.performUse(member);
        return true;
    }

    /**
     * Stops using device.
     * @param device Device to stop using
     * @param member Person stopping usage
     */
    public void stopUsing(Device device, HouseholdMember member) {
        if (device == null || member == null) return;
        device.performStopUsing(member);
    }

    /**
     * Turns on device with validation.
     * @param device Device to turn on
     * @return true if successfully turned on
     */
    public boolean turnOn(Device device){
        if (device == null) return false;
        if (!device.canBeTurnedOn()) return false;
        device.performTurnOn();
        return true;
    }

    /**
     * Turns off device with validation.
     * @param device Device to turn off
     * @return true if successfully turned off
     */
    public boolean turnOff(Device device){
        if (device == null) return false;
        if (!device.canBeTurnedOff()) return false;
        device.performTurnOff();
        return true;
    }

    /**
     * Repairs broken device.
     * Validates that device is broken and can be repaired.
     * @param device Device to repair
     * @param member Person performing repair
     * @return true if successfully repaired
     */
    public boolean repair(Device device, HouseholdMember member){
        if (device == null || member == null) return false;
        if (!device.canBeRepaired()) return false;
        device.performStartRepair();
        return true;
    }
public boolean finishRepair(Device device){
        if (device == null) return false;
        if (device.getDeviceStateEnum() != DeviceState.REPAIRING) return false;
        device.performFinishRepair();
        return true;
    }


    public void finishAllRepairs(){
        for (Device device : devices) {
            if (device.getDeviceStateEnum() == DeviceState.REPAIRING){
                finishRepair(device);
            }
        }
    }

    public void updateConsumptionForAllDevices() {
        for (Device device : devices) {
            device.updateConsumption();
        }
    }
    public List<Device> getUsableDevices(){
        List<Device> usableDevices = new ArrayList<>();
        for (Device device : devices) {
            DeviceType type = device.getType();
            if(type == DeviceType.WASHING_MACHINE || type == DeviceType.STOVE || type == DeviceType.FRIDGE || type == DeviceType.CD_PLAYER){
                usableDevices.add(device);
            }
        }
        return usableDevices;
    }
```

**Důvod:**

- **Zjednodušení pro klienta:** HouseholdMember nemusí znát všechny metody Device (např. canBeUsed(), canBeTurnedOn(), performUse()), stačí mu volat deviceAPI.use()


## Singleton

### Účel
Singleton Pattern zajišťuje, že v systému existuje **právě jedna instance dané třídy** a poskytuje k ní **globální přístupový bod**.  
Pattern se používá pro objekty, které představují **centrální správce stavu** nebo **koordinátory**, například správu událostí.

V tomto projektu je Singleton Pattern použit pro třídu `EventManager`, která:
- centrálně eviduje všechny vzniklé události,
- zajišťuje jejich distribuci handlerům,
- slouží jako jednotný zdroj pravdy pro práci s událostmi v simulaci.

---

### Implementace v kódu

```java
/**
 * Singleton + Iterator Pattern.
 * Central manager for all events in the simulation.
 */
public class EventManager implements Iterable<Event> {

    private static EventManager instance;
    private List<Event> happenedEvents;

    private EventManager() {
        happenedEvents = new ArrayList<>();
    }

    /**
     * Returns singleton instance of EventManager.
     * Creates instance lazily on first access.
     */
    public static EventManager getInstance() {
        if (instance == null) {
            instance = new EventManager();
        }
        return instance;
    }

    public Event generateEvent(EventSource source, Object target, EventType eventType) {
        Event event = new Event(source, target, eventType);
        addEvent(event);
        return event;
    }

    public void addEvent(Event event) {
        happenedEvents.add(event);
    }
}
```
### Příklad použití
```java
@Override
public void eventOccuired() {
    EventManager em = EventManager.getInstance();

    if (role.getRoleName() == RoleName.BABY) {
        if (Math.random() < 0.1) {
            em.generateEvent(this, this, EventType.BABY_CRYING);
            return;
        }
    }

    if (role.getRoleName() == RoleName.PET) {
        if (Math.random() < 0.1) {
            em.generateEvent(this, this, EventType.PET_HUNGRY);
        }
    }
}
```
## Důvod použití

Pattern byl v projektu zvolen z následujících důvodů:

- **Jednotný zdroj pravdy** – všechny události v systému jsou spravovány jedním centrálním objektem  
- **Globální dostupnost** – `EventManager` je snadno dostupný z libovolného místa (zařízení, osoby, senzory)  
- **Konzistence systému** – všechny části simulace pracují se stejným seznamem událostí  
- **Kontrola životního cyklu** – instance je vytvářena pomocí *lazy* přístupu a nemůže vzniknout vícekrát  

---

## Iterator

### Účel
Iterator Pattern poskytuje **jednotný způsob procházení kolekce objektů**, aniž by bylo nutné znát její vnitřní reprezentaci.  
Odděluje logiku iterace od logiky zpracování dat a zvyšuje **čitelnost, flexibilitu a udržovatelnost kódu**.

V projektu je Iterator Pattern použit pro **iteraci nad událostmi uloženými v `EventManager`**, zejména při generování reportů a statistik.

### Implementace v kódu

```java
/**
 * Enables iteration over all happened events.
 */
@Override
public Iterator<Event> iterator() {
    return happenedEvents.iterator();
}

```
### Příklad použití
```java
private Map<EventType, List<Event>> groupByType() {
    Map<EventType, List<Event>> map = new EnumMap<>(EventType.class);

    for (Event e : eventManager) {
        map.computeIfAbsent(e.getEventType(), k -> new ArrayList<>())
                .add(e);
    }
    return map;
}
```
## Důvod použití

Iterator Pattern byl v projektu zvolen z následujících důvodů:

- **Zapouzdření dat** – reporty nemají přímý přístup k interní kolekci událostí  
- **Čisté API** – `EventManager` se chová jako kolekce bez odhalení své vnitřní implementace  
- **Snadná rozšiřitelnost** – interní datovou strukturu (`List`, `Set`, `Queue`) lze změnit bez dopadu na klientský kód  
- **Podpora for-each smyček** – přirozené, přehledné a čitelné zpracování událostí  

# Spuštění aplikace
---

## Konfigurační soubor

Konfigurace simulace je uložena v externím souboru `simulation.json`.  
Tento soubor definuje:

- strukturu domu (podlaží, místnosti),
- zařízení a senzory v jednotlivých místnostech,
- sportovní vybavení,
- členy domácnosti a jejich role.

Díky použití **JSON konfigurace** je možné měnit scénář simulace **bez zásahu do zdrojového kódu**, což výrazně zvyšuje flexibilitu a rozšiřitelnost aplikace.

---

## Vstupní bod aplikace

Celá aplikace se spouští ze třídy `Main`, která obsahuje metodu `main`.

```java
public static void main(String[] args) {
    SimulationConfig config = new SimulationConfig();

    Simulation simulation =
        config.createSimulationFromJson("simulation.json");

    simulation.run(100);

    ...
}
```
## Vytvoření simulace

Třída `SimulationConfig` zajišťuje kompletní inicializaci simulačního prostředí:

- načte konfigurační soubor `simulation.json`,
- vytvoří instanci domu (`House`),
- vytvoří členy domácnosti (`HouseholdMember`),
- inicializuje zařízení, senzory a sportovní vybavení,
- připraví centrální instanci `DeviceAPI`.

Výsledkem je instance třídy `Simulation`, která obsahuje **kompletní stav simulovaného světa** a je připravena ke spuštění.

---

## Průběh simulace

Simulace probíhá v **diskrétních simulačních krocích** (*simulation steps*).  
Počet simulačních kroků je nastavitelný voláním metody:
```java
simulation.run(POCET_KROKU);
```

Například:
```java
simulation.run(100);
```

### Každý simulační krok zahrnuje:

- chování členů domácnosti (používání zařízení, sportovní aktivity, pohyb),
- aktualizaci spotřeby zařízení,
- generování a zpracování událostí  
  (např. poruchy, data ze senzorů, výpadky elektřiny),
- automatické systémové zásahy  
  (např. vypnutí zařízení),
- dokončení oprav a probíhajících aktivit.

---

## Výstupy simulace

Po skončení simulace jsou automaticky generovány následující reporty:

- **Aktivity a využití zařízení** (`ActivityAndUsageReport`)
- **Konfigurace domu** (`HouseConfigurationReport`)
- **Spotřeba energie** (`ConsumptionReport`)
- **Události během simulace** (`EventReport`)

Reporty jsou ukládány do **textových souborů** v pracovním adresáři aplikace a slouží k dalšímu vyhodnocování průběhu simulace.

