# Invoice API

## Description

This is a REST API for an invoice systems built using Scala. An invoice is defined as:
```
{
    "_id": "5947235934c2221db6a80d1c",
    "customerId": 123,
    "addressId": 321,
    "type": "shop",
    "amount": 15.99,
    "createdAt": 1497835229171
}
```

## TODO

- [ ] Unit tests
- [ ] Integration tests
- [ ] API tests
- [ ] Refactor handlers
- [ ] Fix filter issue
- [ ] Do load test

## Endpoints

- List customer invoices with filter: `[GET] /v1/customer/:customerId/invoices[?{filter, month}]`

- List customer invoices linked to address: `[GET]  /v1/customer/:customerId/addresses/:addressId/invoices`

- Create an invoice: `[POST] /v1/customers/:customerId/addresses/:addressId/invoices`
```
{
    "type": "shop",
    "amount": 15.99
}
```

## Running the project

```
$ sbt run
```
The application starts at `http://localhost:8080`