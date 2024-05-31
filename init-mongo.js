db = db.getSiblingDB('admin');

db.createUser({
    user: 'root',
    pwd: 'rootroot',
    roles: [
        { role: 'readWrite', db: 'notePatient' },
        { role: 'readWrite', db: 'admin' }
    ]
});
