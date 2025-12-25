export interface Ciudad {
  id: number;
  nombre: string;
  imagen: string;
}

export interface Sede {
  id: number;
  nombre: string;
  direccion: string;
  imagenUrl: string;
  
  ciudad: Ciudad;
}

export interface Campo {
  id: number;
  nombre: string;
  precioHora: number;
  descripcion: string;
  imagen: string;
  disponible: boolean;
  sede: Sede;
}

export interface ReservaRequest {
  campoId: number;
  fecha: string;      // YYYY-MM-DD
  horaInicio: string; // HH:mm
  horaFin: string;
  nombreInvitado?: string;
  emailInvitado?: string;
  telefonoInvitado?: string;
}

export interface Pago {
  id: number;
  monto: number;
  metodoPago: string;
  fechaPago: string;   // ISO string
  reservaId: number;   // puedes mapearlo si tu entidad lo expone
}


export interface PagoRequest {
  monto: number;
  metodoPago: string; // TARJETA, YAPE, PLIN
  titular?: string;
  numeroTarjeta?: string;
  vencimiento?: string;
  cvv?: string;
}

export interface Reserva {
  id: number;
  campo: Campo;
  fecha: string;
  horaInicio: string;
  horaFin: string;
  invitado?: string;
}
export interface Usuario {
  id: number;
  nombre: string;
  email: string;
  telefono: string;
}
export interface RegistroRequest {
  nombre: string;
  email: string;
  telefono: string;
  password: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface UsuarioResponse {
  id: number;
  nombre: string;
  email: string;
  telefono: string;
  rol: string; // "ROLE_USER" o "ROLE_ADMIN"
}

