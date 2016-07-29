# EncryptThis
Java application that performs AES file encryption and decryption

When the application is started, the UI is very minimal as it only contains a single editable text area where the user can enter the file system path to the specified file or they can use the associated button to open a file chooser to select a file.  Three buttons below the file selection are the available options, which are "Encrypt", "Decrypt", and "Exit".

The Text Area at the bottom is an output area that shows that status of Encryption or Decryption success or failure.  If Encryption is successful, the location of the encrypted file with extension .aes is shown.  The same is true for decryption.  The output shows the files original filename (without the aes extension).

When Encrypt or Decrypt is selected, the user is presented with a dialog box prompting the user to enter a password. On Encryption, the password is combined with a salt and then used to generate a private key.  An Initialization Vector is then generated and The encryption cipher is initialized.  The selected file is then read into the cipher and output to a new file with the salt and iv.

When Decryption is selected, the user is again prompted to enter a password.  The salt and IV are read in from the encrypted file, the cipher initialized in DECRYPT_MODE, and the remaining ciphertext is passed to the cipher and decrypted.  Output is the normal file of its originating type (txt, pdf, jpg, etc..).


At this point in time, the application is only capable of encryption and decryption of individual files locally on the users' system.

Will be expanded to include directory encryption and possible usage with web servers.
