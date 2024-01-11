# FTPServer

Jusqu'à présent, j'ai passé du temps à essayer d'utiliser FileZilla comme serveur FTP et à l'implémenter dans Eclipse. Au début, je pensais que cela serait beaucoup plus facile, mais il s'avère que FileZilla est principalement un client FTP, pas un serveur FTP. Par conséquent, mon approche initiale n'était pas réalisable. À la place, j'ai opté pour une autre solution : Apache FTP Server. Cependant, j'ai rencontré des difficultés car la documentation n'était pas claire. Plus tard, j'ai appris d'un camarade de classe que nous devrions utiliser uniquement ce que nous avons vu dans la session d'apprentissage d'aujourd'hui pour créer un serveur FTP simple en utilisant des sockets. C'est ce sur quoi je travaille maintenant, où j'ai choisi de créer trois classes : une pour gérer le serveur, une deuxième pour l'utilisateur, et une troisième pour la fonctionnalité principale. Je suppose que c'est ce que j'obtiens en essayant d'être un peu créatif.

ChatGPT génère un code qui fonctionne correctement, mais il suit un style particulier qui peut ne pas sembler entièrement original.
