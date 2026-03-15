# Java RMI Assignment: Database Server

## Assignment

Implement a server that provides basic data storage capabilities. The server must be able to handle multiple **databases** and multiple **clients** simultaneously.

---

## Data Structures

The server and client will use the following data structures (objects):

### `dbname`
- **Type:** `String`
- Database identifier — a text string (max length: 50)

### `DBRecord`
A structure containing a single database record, consisting of:

| Field | Type | Description | Example |
|---|---|---|---|
| `tscreate` | `String` | Timestamp of record creation on the server | `"2011-09-23 14:34:56"` |
| `tsmodify` | `String` | Timestamp of the last modification on the server | `"2011-10-02 14:34:57"` |
| `key` | `Integer` | Unique identifier (key) | |
| `message` | `String` | The actual message/payload | |

---

## Server-Provided Functions (Methods)

### `String[] listDB()`
Lists all existing databases.

### `boolean createDB(String dbname) throws DBExistException`
Creates a database with the given name.

### `Integer insert(String dbname, Integer key, String message) throws DBNotFoundException, DuplicateKeyException`
Creates a new record in the specified database.

### `Integer update(String dbname, Integer key, String message) throws DBNotFoundException, KeyNotFoundException`
Updates the record identified by `key` with the new `message` value.

### `DBRecord get(String dbname, Integer key) throws DBNotFoundException, KeyNotFoundException`
Returns the record associated with the given key.

### `DBRecord[] getA(String dbname, Integer[] key) throws DBNotFoundException, KeyNotFoundException`
Returns an array of records for the given keys. The operation succeeds only if **all** keys are found.

### `void flush()`
Writes all in-memory changes to disk.

---

## Implementation Notes

- Each database is represented by a single file (e.g., database `osoby` → file `osoby.dbcsv`).
- Records are stored in **CSV format**.
- For simplicity, the in-memory database does **not** need to be continuously synchronized with the file representation — `flush()` handles synchronization.
- On server startup, the server state is restored from the saved files (i.e., it resumes from where it left off).
- Multiple clients working with **different databases** must be supported simultaneously — **atomicity of certain operations is required**.

> **Note:** It is recommended to avoid strings containing quotes (`"`) or semicolons (`;`) in all `String` variables to prevent CSV parsing conflicts.

### Example Database File — `adresy.dbcsv`

```csv
"2011-09-23 14:34:56";"2011-09-24 14:34:56";"1";"Jan Novotný, Zikova 7, Praha"
"2010-03-20 18:23:56";"2011-09-21 14:26:56";"256";"Adéla Krátká, Lovosice 345"
"2011-08-20 14:23:56";"2011-09-24 14:24:56";"666";"Dr. Zlo, Pekelná 666, Gomora"
```
## Batch Processing
To speed up submission evaluation, tasks must be runnable from the command line, and the client must support batch processing.

### Server
```
./server [IP_address] [port_number]
```
- IP_address and port_number specify where the server should listen.

- If no parameters are provided, the server listens on all network interfaces using default ports.

### Client
```
./client IP_address port_number config_file
```
- IP_address and port_number specify where the server is located.

- config_file contains a sequence of commands for the client.

Commands are written in CSV format and mirror the available server functions.
If a command results in an error (exception), the user is notified and execution continues with the next command.

### Example Config File
```text
"listdb"
"createdb";"adresy"
"insert";"adresy";"1";"Jan Novotný, Zikova 7, Praha"
"insert";"adresy";"256";"Adéla Krátká, Lovosice 345"
"insert";"adresy";"666";"Dr. Zlo, Pekelná 666, Gomora"
"update";"adresy";"256";"Adéla Dlouhá, Lovosice 345"
```

## Sample Output
### Config File Contents
```
"listdb"
"insert";"adresy";"1";"Jan Novotný, Zikova 7, Praha"
"createdb";"adresy"
"listdb"
"insert";"adresy";"256";"Adéla Krátká, Lovosice 345"
"update";"adresy";"256";"Adéla Dlouhá, Lovosice 345"
"ghCHr6";"parametr"
```
### Client Application Output
```
>> listdb
<< Databases:

>> Inserting into database "adresy" record ["1";"Jan Novotný, Zikova 7, Praha"]
<< ERROR - Database "adresy" does not exist

>> createdb adresy
<< Database "adresy" created

>> listdb
<< Databases: 'adresy'

>> Inserting into database "adresy" record ["256";"Adéla Krátká, Lovosice 345"]
<< DB "adresy" - record inserted

>> Updating record "256" in database "adresy" with "Adéla Dlouhá, Lovosice 345"
<< DB "adresy" - record updated

>> ERROR – command "ghCHr6" not implemented
```
