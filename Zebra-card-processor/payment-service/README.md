Idea:

When client presses checkout, merchant sends payment intent, then "payment method required" is sent back.
Client enters card or bank info (inside embedded UI so merchant shouldn't be able to access), sent over TLS/HTTPS.
Vault is a separate service that checks validity of the card or bank info, and then sends back a token that is both universally unique and also only works for the specific merchant.
